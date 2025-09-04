import {Component, inject} from '@angular/core';
import {AppStore} from '../../../../../../shared/store/app.store';
import {NgClass} from '@angular/common';

@Component({
  selector: 'app-transactions-content-body',
  imports: [
    NgClass
  ],
  templateUrl: './transactions-content-body.component.html',
  styleUrl: './transactions-content-body.component.css'
})
export class TransactionsContentBodyComponent {
  store = inject(AppStore);

}
