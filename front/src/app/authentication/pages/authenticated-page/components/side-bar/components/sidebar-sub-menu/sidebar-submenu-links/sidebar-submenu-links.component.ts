import {Component, inject, Input} from '@angular/core';
import {RouterLink, RouterLinkActive} from '@angular/router';
import {AppStore} from '../../../../../../../../shared/store/app.store';

@Component({
  selector: 'app-sidebar-submenu-links',
  templateUrl: './sidebar-submenu-links.component.html',
  imports: [
    RouterLink,
    RouterLinkActive
  ],
  styleUrls: ['./sidebar-submenu-links.component.css']
})
export class SidebarSubmenuLinksComponent {
  @Input() title!: string;
  @Input() route!: string;
  @Input() requireCompanySelection: boolean = false;

  protected store = inject(AppStore)
}
