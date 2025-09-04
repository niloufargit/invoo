import {Component, effect, inject, OnInit, signal} from '@angular/core';
import {GetCompanyByIdResponse} from '../../../../../../../settings/companies/models/company.models';
import {AppStore} from '../../../../../../../shared/store/app.store';

@Component({
  selector: 'app-select-company',
  imports: [
  ],
  templateUrl: './select-company.component.html',
  styleUrl: './select-company.component.css'
})
export class SelectCompanyComponent implements OnInit {

  protected store = inject(AppStore);
  protected selectedCompany: GetCompanyByIdResponse | null = Object.create(null);
  protected isOpen: boolean = false;

  constructor() {
    effect(() => {
      this.selectedCompany = this.store.selectedCompany();
      if (this.selectedCompany?.id) {
        this.store.iniDataCatalogs();
      } else {
        this.store.clearData();
      }

    });


  }

  ngOnInit() {
    this.selectedCompany = this.store.selectedCompany();
  }

  toggleDropdown() {
    this.isOpen = !this.isOpen;
  }

  selectCompany(company: GetCompanyByIdResponse) {
    if (this.store.selectedCompany()?.id === company?.id) {
      this.store.unselectCompany();
      this.selectedCompany = null;
      this.isOpen = false;
    } else {
      this.store.selectCompany(company);
      this.selectedCompany = company;
      this.isOpen = false;
    }
  }

}
