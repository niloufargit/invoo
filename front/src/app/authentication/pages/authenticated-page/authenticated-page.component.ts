import {Component, inject, OnInit} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {SideBarComponent} from './components/side-bar/side-bar.component';
import {AppStore} from '../../../shared/store/app.store';

@Component({
  selector: 'app-authenticated-page',
  imports: [
    RouterOutlet,
    SideBarComponent
  ],
  templateUrl: './authenticated-page.component.html',
  styleUrl: './authenticated-page.component.css'
})

export class AuthenticatedPage implements OnInit {

  protected store = inject(AppStore);
  async ngOnInit(): Promise<void> {
    await this.store.getCurrentUser();
    this.store.getAllCompanies();
    this.store.getAllBeneficiariesByIdCompany();
    this.store.getAllCatalogs();
    this.store.getProductsByCatalogId(this.store.selectedCatalog()?.id.toString() ?? "");
    this.store.getAllPaymentRequestsByCompanyId();
    this.store.getAllInvoicesByIdCompany();
    this.store.getAllPaymentRequestsReceivedByCompanyId();

  }
}

