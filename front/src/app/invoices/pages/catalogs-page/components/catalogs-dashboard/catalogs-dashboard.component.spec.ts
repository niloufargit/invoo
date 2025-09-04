import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CatalogsDashboardComponent } from './catalogs-dashboard.component';

describe('CatalogsDashboardComponent', () => {
  let component: CatalogsDashboardComponent;
  let fixture: ComponentFixture<CatalogsDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CatalogsDashboardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CatalogsDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
