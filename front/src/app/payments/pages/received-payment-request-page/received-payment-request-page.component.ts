import { Component } from '@angular/core';
import {PageTemplateComponent} from '../../../shared/components/page-template/page-template.component';
import {PaymentRequestReceivedComponent} from './payment-request-received/payment-request-received.component';

@Component({
  selector: 'app-received-payment-request-page',
  imports: [
    PageTemplateComponent,
    PaymentRequestReceivedComponent
  ],
  templateUrl: './received-payment-request-page.component.html',
  styleUrl: './received-payment-request-page.component.css'
})
export class ReceivedPaymentRequestPageComponent {

}
