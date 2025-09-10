import { Component, computed, input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-progress-bar',
  templateUrl: './progress-bar.html',
  styleUrls: ['./progress-bar.css'],
  standalone: true,
  imports: [CommonModule]
})
export class ProgressBarComponent {
  currentStep = input.required<number>();

  steps = [
    { step: 1, title: 'Project Info' },
    { step: 2, title: 'Technology Stack' },
    { step: 3, title: 'Infrastructure' },
    { step: 4, title: 'Review & Generate' }
  ];

  progressWidth = computed(() => {
    return ((this.currentStep() - 1) / (this.steps.length - 1)) * 100 + '%';
  });
}
