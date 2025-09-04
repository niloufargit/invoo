import {Component} from '@angular/core';
import {CardComponentComponent} from '../../../shared/components/cards/card-component/card-component.component';
import {PageTemplateComponent} from '../../../shared/components/page-template/page-template.component';
import {FormsModule} from '@angular/forms';
import {
  ListInvoiceGenerateContentComponent
} from './components/list-invoice-generate-content/list-invoice-generate-content.component';

@Component({
  selector: 'app-list-invoice-generate',
  imports: [
    CardComponentComponent,
    PageTemplateComponent,
    FormsModule,
    ListInvoiceGenerateContentComponent,
  ],
  templateUrl: './list-invoice-generate.component.html',
  styleUrl: './list-invoice-generate.component.css'
})
export class ListInvoiceGenerateComponent {

}
