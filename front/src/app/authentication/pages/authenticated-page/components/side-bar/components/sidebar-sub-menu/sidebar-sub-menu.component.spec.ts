import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarSubMenuComponent } from './sidebar-sub-menu.component';

describe('SidebarSubMenuComponent', () => {
  let component: SidebarSubMenuComponent;
  let fixture: ComponentFixture<SidebarSubMenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SidebarSubMenuComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SidebarSubMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
