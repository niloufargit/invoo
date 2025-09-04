import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SuppliersPage } from './suppliers-page.component';

describe('SuppliersPageComponent', () => {
  let component: SuppliersPage;
  let fixture: ComponentFixture<SuppliersPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SuppliersPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SuppliersPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
