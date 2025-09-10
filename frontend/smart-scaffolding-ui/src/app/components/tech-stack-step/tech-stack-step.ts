import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WizardStateService } from '../../services/wizard-state';

@Component({
  selector: 'app-tech-stack-step',
  templateUrl: './tech-stack-step.html',
  styleUrls: ['./tech-stack-step.css'],
  standalone: true,
  imports: [CommonModule]
})
export class TechStackStepComponent {
  wizardState = inject(WizardStateService);

  backendTechs = [
    { id: 'spring-boot', name: 'Spring Boot', description: 'Java enterprise framework with OAuth2', icon: '☕' },
    { id: 'node-express', name: 'Node.js + Express', description: 'JavaScript backend with JWT auth', icon: '🟢' },
    { id: 'dotnet-core', name: '.NET Core', description: 'Microsoft\'s cross-platform framework', icon: '🔷' }
  ];

  frontendTechs = [
    { id: 'react', name: 'React', description: 'Modern web UI library', icon: '⚛️' },
    { id: 'angular', name: 'Angular', description: 'Full-featured framework', icon: '🅰️' },
    { id: 'vue', name: 'Vue.js', description: 'Progressive framework', icon: '💚' }
  ];

  mobileTechs = [
    { id: 'flutter', name: 'Flutter', description: 'Cross-platform mobile apps', icon: '🐦' },
    { id: 'react-native', name: 'React Native', description: 'Native mobile development', icon: '📱' },
    { id: 'none', name: 'No Mobile', description: 'Web-only application', icon: '❌' }
  ];

  databases = [
    { value: 'postgresql', name: 'PostgreSQL' },
    { value: 'mysql', name: 'MySQL' },
    { value: 'mongodb', name: 'MongoDB' },
    { value: 'oracle', name: 'Oracle' },
    { value: 'sql-server', name: 'SQL Server' }
  ];

  updateTech(category: 'backend' | 'frontend' | 'mobile', techId: string) {
    this.wizardState.updateTechStack({ [category]: techId });
  }

  updateDatabase(event: Event) {
    const selectElement = event.target as HTMLSelectElement;
    this.wizardState.updateTechStack({ database: selectElement.value });
  }
}
