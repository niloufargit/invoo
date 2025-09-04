import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListInvoiceGenerateContentComponent } from './list-invoice-generate-content.component';

describe('ListInvoiceGenerateContentComponent', () => {
  let component: ListInvoiceGenerateContentComponent;
  let fixture: ComponentFixture<ListInvoiceGenerateContentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListInvoiceGenerateContentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListInvoiceGenerateContentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
