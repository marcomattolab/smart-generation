import { Injectable } from '@angular/core';
import { pluginRegistry, Plugin } from './plugin-registry';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WizardStateService {
  private readonly _plugins = new BehaviorSubject<Plugin[]>(Object.values(pluginRegistry));
  readonly plugins$ = this._plugins.asObservable();

  private readonly _selectedPlugins = new BehaviorSubject<Plugin[]>(this.getInitialSelectedPlugins());
  readonly selectedPlugins$ = this._selectedPlugins.asObservable();

  private readonly _pluginConfigurations = new BehaviorSubject<{ [key: string]: any }>(this.getInitialPluginConfigurations());
  readonly pluginConfigurations$ = this._pluginConfigurations.asObservable();

  private readonly _wizardSteps = new BehaviorSubject<any[]>([]);
  readonly wizardSteps$ = this._wizardSteps.asObservable();

  private readonly _currentStep = new BehaviorSubject<number>(1);
  readonly currentStep$ = this._currentStep.asObservable();

  constructor() {
    this.generateWizardSteps();
  }

  private getInitialSelectedPlugins(): Plugin[] {
    return Object.values(pluginRegistry).filter(p => p.enabled);
  }

  private getInitialPluginConfigurations(): { [key: string]: any } {
    const configs: { [key: string]: any } = {};
    this.getInitialSelectedPlugins().forEach(plugin => {
      configs[plugin.id] = this.getDefaultConfiguration(plugin);
    });
    return configs;
  }

  private getDefaultConfiguration(plugin: Plugin): any {
    const config: any = {};
    if (plugin.configSchema) {
      plugin.configSchema.fields.forEach(field => {
        if (field.type === 'checkbox' && Array.isArray(field.options)) {
          config[field.name] = field.options.filter((option: any) => option.default).map((option: any) => option.value);
        } else {
          config[field.name] = field.default;
        }
      });
    }
    return config;
  }

  generateWizardSteps() {
    const steps = [
      {
        id: 'project-info',
        title: 'Project Info',
        icon: '📋'
      }
    ];

    this._selectedPlugins.getValue().forEach(plugin => {
      if (plugin.configSchema && plugin.configSchema.fields.length > 0) {
        steps.push({
          id: `plugin-${plugin.id}`,
          title: plugin.name.split(' ')[0],
          icon: plugin.icon,
        });
      }
    });

    steps.push({
      id: 'review-generate',
      title: 'Review & Generate',
      icon: '🚀'
    });

    this._wizardSteps.next(steps);
  }

  nextStep() {
    if (this._currentStep.getValue() < this._wizardSteps.getValue().length) {
      this._currentStep.next(this._currentStep.getValue() + 1);
    }
  }

  previousStep() {
    if (this._currentStep.getValue() > 1) {
      this._currentStep.next(this._currentStep.getValue() - 1);
    }
  }

  togglePlugin(plugin: Plugin) {
    const selectedPlugins = this._selectedPlugins.getValue();
    const pluginConfigs = this._pluginConfigurations.getValue();

    const index = selectedPlugins.findIndex(p => p.id === plugin.id);
    if (index > -1) {
      selectedPlugins.splice(index, 1);
      delete pluginConfigs[plugin.id];
    } else {
      selectedPlugins.push(plugin);
      pluginConfigs[plugin.id] = this.getDefaultConfiguration(plugin);
    }

    this._selectedPlugins.next([...selectedPlugins]);
    this._pluginConfigurations.next({ ...pluginConfigs });
    this.generateWizardSteps();
  }

  updatePluginConfiguration(pluginId: string, fieldName: string, value: any) {
    const pluginConfigs = this._pluginConfigurations.getValue();
    if (!pluginConfigs[pluginId]) {
      pluginConfigs[pluginId] = {};
    }
    pluginConfigs[pluginId][fieldName] = value;
    this._pluginConfigurations.next({ ...pluginConfigs });
  }
}
