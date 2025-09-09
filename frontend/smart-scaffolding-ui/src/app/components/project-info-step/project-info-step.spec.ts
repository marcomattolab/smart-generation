import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectInfoStep } from './project-info-step';

describe('ProjectInfoStep', () => {
  let component: ProjectInfoStep;
  let fixture: ComponentFixture<ProjectInfoStep>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProjectInfoStep]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProjectInfoStep);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
