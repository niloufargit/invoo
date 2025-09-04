import { Component } from '@angular/core';
import {BeneficiaryContentHeaderComponent} from './beneficiary-content-header/beneficiary-content-header.component';
import {BeneficiaryContentBodyComponent} from './beneficiary-content-body/beneficiary-content-body.component';

@Component({
  selector: 'app-beneficiary-content',
  imports: [
    BeneficiaryContentHeaderComponent,
    BeneficiaryContentBodyComponent
  ],
  templateUrl: './beneficiary-content.component.html',
  styleUrl: './beneficiary-content.component.css'
})
export class BeneficiaryContentComponent {

}
