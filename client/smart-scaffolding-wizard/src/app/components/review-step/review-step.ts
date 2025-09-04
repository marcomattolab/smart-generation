import { Component, OnInit, OnDestroy } from '@angular/core';
import { WizardStateService } from '../../services/wizard-state';
import { Plugin } from '../../services/plugin-registry';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-review-step',
  templateUrl: './review-step.html',
  styleUrls: ['./review-step.css'],
  standalone: true,
  imports: [CommonModule]
})
export class ReviewStepComponent implements OnInit, OnDestroy {
  projectName = 'Untitled Project';
  organization = 'ELCA';
  selectedPlugins: Plugin[] = [];
  pluginConfigurations: { [key: string]: any } = {};
  private subscriptions = new Subscription();

  constructor(private readonly wizardState: WizardStateService) {}

  ngOnInit() {
    this.subscriptions.add(this.wizardState.selectedPlugins$.subscribe(plugins => this.selectedPlugins = plugins));
    this.subscriptions.add(this.wizardState.pluginConfigurations$.subscribe(configs => this.pluginConfigurations = configs));
  }

  ngOnDestroy() {
    this.subscriptions.unsubscribe();
  }

  getPluginConfig(pluginId: string) {
    return this.pluginConfigurations[pluginId];
  }

  generateProject() {
    document.getElementById('reviewContent')!.style.display = 'none';
    document.getElementById('loadingAnimation')!.classList.remove('hidden');

    setTimeout(() => {
        document.getElementById('loadingAnimation')!.classList.add('hidden');
        document.getElementById('successMessage')!.classList.remove('hidden');
    }, 3000);
  }

  downloadProject() {
    const pluginList = this.selectedPlugins.map(p => p.name).join('\\n• ');
    alert(`🎉 Project "${this.projectName}" would be downloaded with:\\n\\n` +
          `Selected Plugins:\\n• ${pluginList}\\n\\n` +
          `Generated Files:\\n` +
          `• Complete source code scaffolding\\n` +
          `• Plugin-specific configurations\\n` +
          `• Docker and deployment files\\n` +
          `• CI/CD pipeline configurations\\n` +
          `• Documentation and setup guides\\n\\n` +
          `This is a POC demo - in production, this would generate and download the actual project.`);
  }
}
