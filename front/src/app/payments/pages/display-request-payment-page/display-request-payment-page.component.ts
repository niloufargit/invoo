import {Component, inject} from '@angular/core';
import {AppStore} from '../../../shared/store/app.store';
import {NgClass} from '@angular/common';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-display-request-payment-page',
  imports: [
    NgClass,
    RouterLink
  ],
  templateUrl: './display-request-payment-page.component.html',
  styleUrl: './display-request-payment-page.component.css'
})
export class DisplayRequestPaymentPageComponent {

  store = inject(AppStore)

}
