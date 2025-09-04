import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaymentRequestReceivedComponent } from './payment-request-received.component';

describe('PaymentRequestReceivedComponent', () => {
  let component: PaymentRequestReceivedComponent;
  let fixture: ComponentFixture<PaymentRequestReceivedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaymentRequestReceivedComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaymentRequestReceivedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
