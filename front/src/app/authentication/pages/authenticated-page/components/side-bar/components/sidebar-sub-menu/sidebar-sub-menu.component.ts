import {Component, inject, Input} from '@angular/core';
import {NgClass} from '@angular/common';
import {AppStore} from '../../../../../../../shared/store/app.store';

@Component({
  selector: 'app-sidebar-sub-menu',
  imports: [
  ],
  templateUrl: './sidebar-sub-menu.component.html',
  styleUrl: './sidebar-sub-menu.component.css'
})
export class SidebarSubMenuComponent {

  @Input() title!: string;
  @Input() icon!: string;
  @Input() requireCompanySelection: boolean = false;

  protected store = inject(AppStore);

  openSubMenu: boolean = true;

  toggleSubMenu() {
    this.openSubMenu = !this.openSubMenu;
  }

}
