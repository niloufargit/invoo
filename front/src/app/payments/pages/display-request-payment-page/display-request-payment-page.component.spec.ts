import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DisplayRequestPaymentPageComponent } from './display-request-payment-page.component';

describe('DisplayRequestPaymentPageComponent', () => {
  let component: DisplayRequestPaymentPageComponent;
  let fixture: ComponentFixture<DisplayRequestPaymentPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DisplayRequestPaymentPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DisplayRequestPaymentPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
