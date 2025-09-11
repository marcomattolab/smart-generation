import { Component } from '@angular/core';
import { PluginManagerComponent } from '../plugin-manager/plugin-manager';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  templateUrl: './header.html',
  styleUrls: ['./header.css'],
  standalone: true,
  imports: [PluginManagerComponent, CommonModule]
})
export class HeaderComponent {
  isPluginManagerOpen = false;

  openPluginManager() {
    this.isPluginManagerOpen = true;
  }

  closePluginManager() {
    this.isPluginManagerOpen = false;
  }
}
