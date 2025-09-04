import {Component, inject, OnInit} from '@angular/core';
import {AppStore} from '../../../../../shared/store/app.store';

@Component({
  selector: 'app-beneficiary-card',
  imports: [],
  templateUrl: './beneficiary-card.component.html',
  standalone: true,
  styleUrl: './beneficiary-card.component.css'
})
export class BeneficiaryCardComponent {
  protected store = inject(AppStore);

  deleteBeneficiary(id: string) {
    this.store.deleteBeneficiary(id);
  }
}
