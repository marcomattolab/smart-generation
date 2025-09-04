import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PluginManager } from './plugin-manager';

describe('PluginManager', () => {
  let component: PluginManager;
  let fixture: ComponentFixture<PluginManager>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PluginManager]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PluginManager);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
