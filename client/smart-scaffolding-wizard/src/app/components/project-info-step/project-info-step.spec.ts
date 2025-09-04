import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ProjectInfoStepComponent } from './project-info-step';

describe('ProjectInfoStep', () => {
  let component: ProjectInfoStepComponent;
  let fixture: ComponentFixture<ProjectInfoStepComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProjectInfoStepComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProjectInfoStepComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
