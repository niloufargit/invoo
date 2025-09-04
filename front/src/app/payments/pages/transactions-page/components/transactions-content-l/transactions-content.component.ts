import {Component} from '@angular/core';
import {TransactionsContentBodyComponent} from './transactions-content-body/transactions-content-body.component';
import {TransactionsContentHeaderComponent} from './transactions-content-header/transactions-content-header.component';

@Component({
  selector: 'app-transactions-content',
  imports: [
    TransactionsContentBodyComponent,
    TransactionsContentHeaderComponent
  ],
  templateUrl: './transactions-content.component.html',
  styleUrl: './transactions-content.component.css'
})
export class TransactionsContentComponent {

}
