import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewStep } from './review-step';

describe('ReviewStep', () => {
  let component: ReviewStep;
  let fixture: ComponentFixture<ReviewStep>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ReviewStep]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReviewStep);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
