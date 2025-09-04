import {Component, inject} from '@angular/core';
import {AppStore} from '../../../../../../../shared/store/app.store';
import {Beneficiary} from '../../../../../../../payments/models/beneficiary.model';

@Component({
  selector: 'app-select-beneficiary',
  imports: [],
  templateUrl: './select-beneficiary.component.html',
  styleUrl: './select-beneficiary.component.css'
})
export class SelectInvoiceBeneficiaryComponent {

  protected store = inject(AppStore);

  selectBeneficiary(beneficiary: Beneficiary) {
    this.store.selectInvoiceBeneficiary(beneficiary);
  }

}
