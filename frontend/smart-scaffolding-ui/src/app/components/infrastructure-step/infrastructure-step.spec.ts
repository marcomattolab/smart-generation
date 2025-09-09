import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfrastructureStep } from './infrastructure-step';

describe('InfrastructureStep', () => {
  let component: InfrastructureStep;
  let fixture: ComponentFixture<InfrastructureStep>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InfrastructureStep]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InfrastructureStep);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
