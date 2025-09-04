import {Component, inject} from '@angular/core';
import {AppStore} from '../../../../shared/store/app.store';

@Component({
  selector: 'app-payment-request-received',
  imports: [],
  templateUrl: './payment-request-received.component.html',
  styleUrl: './payment-request-received.component.css'
})
export class PaymentRequestReceivedComponent {

  store = inject(AppStore)

}
