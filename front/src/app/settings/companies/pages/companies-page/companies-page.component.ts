import {Component, inject} from '@angular/core';
import {Router, RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-companies-page',
  imports: [
    RouterOutlet
  ],
  templateUrl: './companies-page.component.html',
  styleUrl: './companies-page.component.css'
})
export class CompaniesPageComponent {

  router = inject(Router)

  addCompany() {
    this.router.navigate(['/settings/companies/add']);
  }
}
