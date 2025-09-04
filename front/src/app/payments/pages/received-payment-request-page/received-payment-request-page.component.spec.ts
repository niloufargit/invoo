import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReceivedPaymentRequestPageComponent } from './received-payment-request-page.component';

describe('ReceivedPaymentRequestPageComponent', () => {
  let component: ReceivedPaymentRequestPageComponent;
  let fixture: ComponentFixture<ReceivedPaymentRequestPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReceivedPaymentRequestPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReceivedPaymentRequestPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
