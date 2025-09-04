import {Component} from '@angular/core';
import {PageTemplateComponent} from "../../../shared/components/page-template/page-template.component";
import {TransactionsContentComponent} from './components/transactions-content-l/transactions-content.component';

@Component({
  selector: 'app-transactions-page',
  imports: [
    PageTemplateComponent,
    TransactionsContentComponent,
    TransactionsContentComponent
  ],
  templateUrl: './transactions-page.component.html',
  styleUrl: './transactions-page.component.css'
})
export class TransactionsPage {

}
