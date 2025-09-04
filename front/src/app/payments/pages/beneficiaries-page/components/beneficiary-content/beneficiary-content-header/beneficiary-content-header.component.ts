import {Component, effect, inject, signal} from '@angular/core';
import {ModalComponent} from '../../../../../../shared/components/modal/modal.component';
import {
  CardComponentComponent
} from '../../../../../../shared/components/cards/card-component/card-component.component';
import {FormsModule} from '@angular/forms';
import {AppStore} from '../../../../../../shared/store/app.store';
import {BeneficiarySearchResult} from '../../../../../selectors/beneficiary.selector';

@Component({
  selector: 'app-beneficiary-content-header',
  imports: [
    ModalComponent,
    CardComponentComponent,
    FormsModule
  ],
  templateUrl: './beneficiary-content-header.component.html',
  styleUrl: './beneficiary-content-header.component.css'
})
export class BeneficiaryContentHeaderComponent {

  protected store = inject(AppStore);

  protected isOpenBeneficiaryModal = false;
  searchKeyModal = signal<string>('');
  searchedBeneficiary = signal<BeneficiarySearchResult[]>([]);
  searchKeyList = signal<string>('');

  toggleBeneficiaryModal() {
    if (!this.isOpenBeneficiaryModal) {
      this.searchKeyModal.set('');
      this.searchedBeneficiary.set([]);
    }
    this.isOpenBeneficiaryModal = !this.isOpenBeneficiaryModal;
  }

  constructor() {
    effect(() => {
      if (this.searchKeyModal() !== '') {
        this.search();
      } else {
        this.searchedBeneficiary.set([])
      }
    });

    effect(() => {
      this.store.beneficiaryFilterKey(this.searchKeyList())
    });

  }

  async searchProfessionalBeneficiary() {
    this.search();
  }


  search() {
    this.store.searchProfessionalBeneficiary(this.searchKeyModal())
      .then((data) => {
        const b : BeneficiarySearchResult[] = [];
        data.forEach((item: any) => {
          const company: BeneficiarySearchResult = {
            idCompany: item.idCompany,
            name: item.name,
            sirenNumber: item.sirenNumber,
          };
          b.push(company);
        });
        this.searchedBeneficiary.set(b)
      })
      .catch(() => {
        this.searchedBeneficiary.set([]);
        }
      );
  }

  addBeneficiary(item: any) {
    const id = item?.idCompany;
    if (id === undefined || id === null) {
      return;
    }
    this.store.addBeneficiary(null, id, this.store.selectedCompany()?.id)
  }
}
