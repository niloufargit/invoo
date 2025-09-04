import {Component, inject} from '@angular/core';
import {AppStore} from '../../../../../shared/store/app.store';

@Component({
  selector: 'app-transaction-card',
  imports: [],
  templateUrl: './transaction-card.component.html',
  styleUrl: './transaction-card.component.css'
})
export class TransactionCardComponent {
  protected store = inject(AppStore);

}
