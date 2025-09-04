import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SendInvoicesPage } from './send-invoices-page.component';

describe('SendInvoicePageComponent', () => {
  let component: SendInvoicesPage;
  let fixture: ComponentFixture<SendInvoicesPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SendInvoicesPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SendInvoicesPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
