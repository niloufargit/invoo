import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableNewProductComponent } from './table-new-product.component';

describe('TableNewProductComponent', () => {
  let component: TableNewProductComponent;
  let fixture: ComponentFixture<TableNewProductComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TableNewProductComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TableNewProductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
