import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarLinksComponent } from './sidebar-links.component';

describe('SidebarLinksComponent', () => {
  let component: SidebarLinksComponent;
  let fixture: ComponentFixture<SidebarLinksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SidebarLinksComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SidebarLinksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
