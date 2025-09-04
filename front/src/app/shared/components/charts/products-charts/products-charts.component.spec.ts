import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductsChartsComponent } from './products-charts.component';

describe('ProductsChartsComponent', () => {
  let component: ProductsChartsComponent;
  let fixture: ComponentFixture<ProductsChartsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductsChartsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductsChartsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
