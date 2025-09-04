import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarSubmenuLinksComponent } from './sidebar-submenu-links.component';

describe('SidebarLinksComponent', () => {
  let component: SidebarSubmenuLinksComponent;
  let fixture: ComponentFixture<SidebarSubmenuLinksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SidebarSubmenuLinksComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SidebarSubmenuLinksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
