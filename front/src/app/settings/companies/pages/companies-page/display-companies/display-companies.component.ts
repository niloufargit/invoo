import {Component, inject} from '@angular/core';
import {AppStore} from '../../../../../shared/store/app.store';
import {Router} from '@angular/router';

@Component({
  selector: 'app-display-companies',
  imports: [],
  templateUrl: './display-companies.component.html',
  styleUrl: './display-companies.component.css'
})
export class DisplayCompaniesComponent {

  store = inject(AppStore)
  private router = inject(Router);

  selectCompany(company: any) {
    this.store.selectCompany(company);
  }

  editCompany(company: any) {
    this.router.navigate(['/settings/companies/edit'], { queryParams: { data: company.id } });
  }
}
