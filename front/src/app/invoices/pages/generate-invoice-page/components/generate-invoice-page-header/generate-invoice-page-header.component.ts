import {Component, inject} from '@angular/core';
import {AppStore} from '../../../../../shared/store/app.store';
import {ModalComponent} from '../../../../../shared/components/modal/modal.component';
import {CardComponentComponent} from '../../../../../shared/components/cards/card-component/card-component.component';
import {SelectInvoiceBeneficiaryComponent} from './components/select-beneficiary/select-beneficiary.component';

@Component({
  selector: 'app-generate-invoice-page-header',
  imports: [
    ModalComponent,
    CardComponentComponent,
    SelectInvoiceBeneficiaryComponent
  ],
  templateUrl: './generate-invoice-page-header.component.html',
  styleUrl: './generate-invoice-page-header.component.css'
})
export class GenerateInvoicePageHeaderComponent {
  protected store = inject(AppStore);
  protected isBeneficiaryModalOpen = false;

  protected toggleBeneficiaryModal() {
    this.isBeneficiaryModalOpen = !this.isBeneficiaryModalOpen;
  }

}
