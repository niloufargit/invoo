import {Component, effect, inject} from '@angular/core';
import {PageTemplateComponent} from "../../../shared/components/page-template/page-template.component";
import {CardComponentComponent} from '../../../shared/components/cards/card-component/card-component.component';
import {BeneficiaryContentComponent} from './components/beneficiary-content/beneficiary-content.component';
import {AppStore} from '../../../shared/store/app.store';
import {Router} from '@angular/router';

@Component({
  selector: 'app-beneficiaries-page',
  imports: [
    PageTemplateComponent,
    CardComponentComponent,
    BeneficiaryContentComponent
  ],
  templateUrl: './beneficiaries-page.component.html',
  styleUrl: './beneficiaries-page.component.css'
})
export class BeneficiariesPage {

  protected store = inject(AppStore);
  router = inject(Router);

  constructor() {
    effect(() => {
      if (!this.store.selectedCompany()?.id) {
        this.router.navigate(['/']);
      }
    });
  }

}
