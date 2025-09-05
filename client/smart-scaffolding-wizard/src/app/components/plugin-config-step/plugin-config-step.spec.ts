import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PluginConfigStep } from './plugin-config-step';

describe('PluginConfigStep', () => {
  let component: PluginConfigStep;
  let fixture: ComponentFixture<PluginConfigStep>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PluginConfigStep]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PluginConfigStep);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
