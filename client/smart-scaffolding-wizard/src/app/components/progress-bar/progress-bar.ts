import { Component, OnInit, OnDestroy } from '@angular/core';
import { WizardStateService } from '../../services/wizard-state';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-progress-bar',
  templateUrl: './progress-bar.html',
  styleUrls: ['./progress-bar.css'],
  standalone: true,
  imports: [CommonModule]
})
export class ProgressBarComponent implements OnInit, OnDestroy {
  wizardSteps: any[] = [];
  currentStep = 1;
  private subscriptions = new Subscription();

  constructor(private wizardState: WizardStateService) {}

  ngOnInit() {
    this.subscriptions.add(this.wizardState.wizardSteps$.subscribe(steps => this.wizardSteps = steps));
    this.subscriptions.add(this.wizardState.currentStep$.subscribe(step => {
      this.currentStep = step;
      this.updateProgressBarStyle();
    }));
  }

  ngOnDestroy() {
    this.subscriptions.unsubscribe();
  }

  updateProgressBarStyle() {
    const progressWidth = ((this.currentStep - 1) / (this.wizardSteps.length - 1)) * 100;
    const styleId = 'progress-style';
    let styleElement = document.getElementById(styleId) as HTMLStyleElement;
    if (!styleElement) {
      styleElement = document.createElement('style');
      styleElement.id = styleId;
      document.head.appendChild(styleElement);
    }
    styleElement.textContent = `.progress-bar::after { width: ${progressWidth}%; }`;
  }
}
