import {Component, inject} from '@angular/core';
import {AppStore} from '../../../../../../../shared/store/app.store';
import {InvoiceAPI} from '../../../../../../models/invoice.model';

@Component({
  selector: 'app-select-invoice',
  imports: [],
  templateUrl: './select-invoice.component.html',
  styleUrl: './select-invoice.component.css'
})
export class SelectInvoiceComponent {

  protected store = inject(AppStore);

  selectInvoiceCompany(invoice: InvoiceAPI) {
    this.store.selectInvoice(invoice.id);
  }

}
