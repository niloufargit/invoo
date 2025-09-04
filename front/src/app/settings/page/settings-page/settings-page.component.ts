import { Component } from '@angular/core';
import {PageTemplateComponent} from "../../../shared/components/page-template/page-template.component";
import {RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-settings-page',
  imports: [
    PageTemplateComponent,
    RouterOutlet,
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './settings-page.component.html',
  styleUrl: './settings-page.component.css'
})
export class SettingsPage {

}
