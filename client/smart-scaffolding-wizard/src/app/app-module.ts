import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { Header } from './components/header/header';
import { ProgressBar } from './components/progress-bar/progress-bar';
import { Wizard } from './components/wizard/wizard';
import { ProjectInfoStep } from './components/project-info-step/project-info-step';
import { PluginConfigStep } from './components/plugin-config-step/plugin-config-step';
import { ReviewStep } from './components/review-step/review-step';
import { PluginManager } from './components/plugin-manager/plugin-manager';

@NgModule({
  declarations: [
    App,
    Header,
    ProgressBar,
    Wizard,
    ProjectInfoStep,
    PluginConfigStep,
    ReviewStep,
    PluginManager
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [
    provideBrowserGlobalErrorListeners()
  ],
  bootstrap: [App]
})
export class AppModule { }
