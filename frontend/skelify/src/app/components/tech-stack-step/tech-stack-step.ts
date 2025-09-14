import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WizardStateService } from '../../services/wizard-state';

@Component({
  selector: 'app-tech-stack-step',
  templateUrl: './tech-stack-step.html',
  styleUrls: ['./tech-stack-step.scss'],
  imports: [CommonModule],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TechStackStep {
  wizardState = inject(WizardStateService);

  backendTechs = [
    { id: 'spring-boot', name: 'Spring Boot', description: 'Java enterprise framework with OAuth2', iconPath: '/assets/images/wizard/spring-boot.svg' },
    { id: 'node-express', name: 'Node.js + Express', description: 'JavaScript backend with JWT auth', iconPath: '/assets/images/wizard/node-express.svg' },
    { id: 'dotnet-core', name: '.NET Core', description: 'Microsoft\'s cross-platform framework', iconPath: '/assets/images/wizard/dotnet-core.svg' }
  ];

  frontendTechs = [
    { id: 'react', name: 'React', description: 'Modern web UI library', iconPath: '/assets/images/wizard/react.svg' },
    { id: 'angular', name: 'Angular', description: 'Full-featured framework', iconPath: '/assets/images/wizard/angular.svg' },
    { id: 'vue', name: 'Vue.js', description: 'Progressive framework', iconPath: '/assets/images/wizard/vue.svg' }
  ];

  mobileTechs = [
    { id: 'flutter', name: 'Flutter', description: 'Cross-platform mobile apps', iconPath: '/assets/images/wizard/flutter.svg' },
    { id: 'react-native', name: 'React Native', description: 'Native mobile development', iconPath: '/assets/images/wizard/react-native.svg' },
    { id: 'none', name: 'No Mobile', description: 'Web-only application', iconPath: '/assets/images/wizard/none.svg' }
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

  onDatabaseChange(event: Event) {
    const selectElement = event.target as HTMLSelectElement;
    this.wizardState.updateTechStack({ database: selectElement.value });
  }
}
