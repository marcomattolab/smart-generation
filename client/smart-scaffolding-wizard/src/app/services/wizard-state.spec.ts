import { TestBed } from '@angular/core/testing';
import { WizardStateService } from './wizard-state';

describe('WizardState', () => {
  let service: WizardStateService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WizardStateService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
