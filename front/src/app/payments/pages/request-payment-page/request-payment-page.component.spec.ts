import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RequestPaymentPage } from './request-payment-page.component';

describe('RequestPaymentPageComponent', () => {
  let component: RequestPaymentPage;
  let fixture: ComponentFixture<RequestPaymentPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RequestPaymentPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RequestPaymentPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
