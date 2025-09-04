import {Component, Input} from '@angular/core';
import {NgClass} from '@angular/common';
import {RouterLink, RouterLinkActive} from '@angular/router';

@Component({
  selector: 'app-sidebar-links',
  imports: [
    NgClass,
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './sidebar-links.component.html',
  styleUrl: './sidebar-links.component.css'
})
export class SidebarLinksComponent {

  @Input() title: string = "";
  @Input() route: string = "";
  @Input() colorClass: string = "hover:bg-gray-50 hover:text-gray-900 focus:bg-gray-50 focus:text-blue-gray-900 active:bg-gray-50 active:text-gray-900";

}
