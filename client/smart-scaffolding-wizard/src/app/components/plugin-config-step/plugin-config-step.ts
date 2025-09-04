import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { Plugin } from '../../services/plugin-registry';
import { WizardStateService } from '../../services/wizard-state';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-plugin-config-step',
  templateUrl: './plugin-config-step.html',
  styleUrls: ['./plugin-config-step.css'],
  standalone: true,
  imports: [CommonModule, FormsModule]
})
export class PluginConfigStepComponent implements OnInit, OnDestroy {
  @Input() plugin: Plugin | undefined;
  config: any = {};
  private subscriptions = new Subscription();
  private currentStep = 0;

  constructor(private readonly wizardState: WizardStateService) {}

  ngOnInit() {
    this.subscriptions.add(this.wizardState.currentStep$.subscribe(step => {
      this.currentStep = step;
      this.loadPlugin();
    }));
    this.subscriptions.add(this.wizardState.pluginConfigurations$.subscribe(configs => {
        if (this.plugin) {
            this.config = configs[this.plugin.id];
        }
    }));
  }

  ngOnDestroy() {
    this.subscriptions.unsubscribe();
  }

  loadPlugin() {
    this.subscriptions.add(this.wizardState.wizardSteps$.subscribe(steps => {
      const currentStepData = steps[this.currentStep - 1];
      if (currentStepData && currentStepData.id.startsWith('plugin-')) {
        const pluginId = currentStepData.id.replace('plugin-', '');
        this.subscriptions.add(this.wizardState.plugins$.subscribe(plugins => {
            this.plugin = plugins.find(p => p.id === pluginId);
        }));
      }
    }));
  }

  updateConfig(fieldName: string, event: any) {
    const value = event.target.value;
    if (this.plugin) {
      this.wizardState.updatePluginConfiguration(this.plugin.id, fieldName, value);
    }
  }

  toggleCheckbox(fieldName: string) {
    if (this.plugin) {
        const currentValue = this.config[fieldName];
        this.wizardState.updatePluginConfiguration(this.plugin.id, fieldName, !currentValue);
    }
  }

  selectRadio(fieldName: string, value: any) {
    if (this.plugin) {
        this.wizardState.updatePluginConfiguration(this.plugin.id, fieldName, value);
    }
  }
}
