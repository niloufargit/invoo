import { Component } from '@angular/core';
import {BeneficiaryCardComponent} from "../../beneficiary-card/beneficiary-card.component";

@Component({
  selector: 'app-beneficiary-content-body',
  imports: [
    BeneficiaryCardComponent
  ],
  templateUrl: './beneficiary-content-body.component.html',
  standalone: true,
  styleUrl: './beneficiary-content-body.component.css'
})
export class BeneficiaryContentBodyComponent {

}
