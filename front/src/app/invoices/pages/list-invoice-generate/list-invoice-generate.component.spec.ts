import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListInvoiceGenerateComponent } from './list-invoice-generate.component';

describe('ListInvoiceGenerateComponent', () => {
  let component: ListInvoiceGenerateComponent;
  let fixture: ComponentFixture<ListInvoiceGenerateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListInvoiceGenerateComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListInvoiceGenerateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
