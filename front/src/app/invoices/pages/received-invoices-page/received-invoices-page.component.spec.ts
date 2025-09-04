import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReceivedInvoicesPage } from './received-invoices-page.component';

describe('ReceivedInvoicePageComponent', () => {
  let component: ReceivedInvoicesPage;
  let fixture: ComponentFixture<ReceivedInvoicesPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReceivedInvoicesPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReceivedInvoicesPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
