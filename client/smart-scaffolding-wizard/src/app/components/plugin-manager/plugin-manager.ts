import { Component, OnInit, OnDestroy, Output, EventEmitter } from '@angular/core';
import { WizardStateService } from '../../services/wizard-state';
import { Plugin } from '../../services/plugin-registry';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-plugin-manager',
  templateUrl: './plugin-manager.html',
  styleUrls: ['./plugin-manager.css'],
  standalone: true,
  imports: [CommonModule]
})
export class PluginManagerComponent implements OnInit, OnDestroy {
  @Output() closeManager = new EventEmitter<void>();

  allPlugins: Plugin[] = [];
  filteredPlugins: Plugin[] = [];
  selectedPlugins: Plugin[] = [];
  selectedCategory = 'all';
  private subscriptions = new Subscription();

  constructor(private readonly wizardState: WizardStateService) {}

  ngOnInit() {
    this.subscriptions.add(this.wizardState.plugins$.subscribe(plugins => {
      this.allPlugins = plugins;
      this.filterPlugins();
    }));
    this.subscriptions.add(this.wizardState.selectedPlugins$.subscribe(plugins => this.selectedPlugins = plugins));
  }

  ngOnDestroy() {
    this.subscriptions.unsubscribe();
  }

  filterPlugins() {
    if (this.selectedCategory === 'all') {
      this.filteredPlugins = this.allPlugins;
    } else {
      this.filteredPlugins = this.allPlugins.filter(p => p.category === this.selectedCategory);
    }
  }

  selectCategory(category: string) {
    this.selectedCategory = category;
    this.filterPlugins();
  }

  isSelected(plugin: Plugin): boolean {
    return this.selectedPlugins.some(p => p.id === plugin.id);
  }

  togglePlugin(plugin: Plugin) {
    this.wizardState.togglePlugin(plugin);
  }

  apply() {
    this.wizardState.generateWizardSteps();
    this.close();
  }

  close() {
    this.closeManager.emit();
  }
}
