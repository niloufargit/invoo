import {Component, HostListener, inject, OnInit} from '@angular/core';
import {SidebarSubMenuComponent} from './components/sidebar-sub-menu/sidebar-sub-menu.component';
import {
  SidebarSubmenuLinksComponent
} from './components/sidebar-sub-menu/sidebar-submenu-links/sidebar-submenu-links.component';
import {SidebarLinksComponent} from './components/sidebar-links/sidebar-links.component';
import {MobileViewService} from '../../../../../shared/responsive/service/mobile-view.service';
import {ModalComponent} from '../../../../../shared/components/modal/modal.component';
import {NgTemplateOutlet} from '@angular/common';
import {AppStore} from '../../../../../shared/store/app.store';

import {
  SelectCompanyComponent
} from './components/select-company/select-company.component';
import {AuthService} from '../../../../services/auth.service';

@Component({
  selector: 'app-side-bar',
  imports: [
    SidebarSubMenuComponent,
    SidebarSubmenuLinksComponent,
    SidebarLinksComponent,
    ModalComponent,
    NgTemplateOutlet,
    SelectCompanyComponent,
  ],
  templateUrl: './side-bar.component.html',
  standalone: true,
  styleUrl: './side-bar.component.css'
})
export class SideBarComponent implements OnInit {

  isMobile = false;
  protected isSidebarOpen = false;
  private mobileView = inject(MobileViewService);
  protected store = inject(AppStore);
  private authService = inject(AuthService)
  ngOnInit() {
    this.isMobile = this.mobileView.isMobile();
    if(!this.isMobile) {
      this.isSidebarOpen = true;
    }
    if(this.isMobile){
      this.isSidebarOpen = false;
    }
  }

  @HostListener('window:resize')
  onResize() {
    this.isMobile = this.mobileView.isMobile();
    if(!this.isMobile) {
      this.isSidebarOpen = true;
    }
    if(this.isMobile){
      this.isSidebarOpen = false;
    }
  }

  toggleSidebar() {
    this.isSidebarOpen = !this.isSidebarOpen;
  }

  async logout() {
    await this.authService.logout()
  }
}
