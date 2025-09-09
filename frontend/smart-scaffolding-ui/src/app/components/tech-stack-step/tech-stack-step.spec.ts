import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TechStackStep } from './tech-stack-step';

describe('TechStackStep', () => {
  let component: TechStackStep;
  let fixture: ComponentFixture<TechStackStep>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TechStackStep]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TechStackStep);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
