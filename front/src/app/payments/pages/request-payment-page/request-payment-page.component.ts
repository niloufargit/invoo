import {Component, inject} from '@angular/core';
import {PageTemplateComponent} from "../../../shared/components/page-template/page-template.component";
import {NgClass} from '@angular/common';
import {ModalComponent} from '../../../shared/components/modal/modal.component';
import {CardComponentComponent} from '../../../shared/components/cards/card-component/card-component.component';
import {
  SelectInvoiceBeneficiaryComponent
} from '../../../invoices/pages/generate-invoice-page/components/generate-invoice-page-header/components/select-beneficiary/select-beneficiary.component';
import {AppStore} from '../../../shared/store/app.store';
import {
  DisplayRequestPaymentPageComponent
} from '../display-request-payment-page/display-request-payment-page.component';
import {
  SelectInvoiceComponent
} from '../../../invoices/pages/generate-invoice-page/components/generate-invoice-page-header/components/select-invoice/select-invoice.component';
import {PaymentRequest} from '../../models/paymentRequest.model';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-request-payment-page',
  imports: [
    PageTemplateComponent,
    NgClass,
    ModalComponent,
    CardComponentComponent,
    SelectInvoiceBeneficiaryComponent,
    DisplayRequestPaymentPageComponent,
    SelectInvoiceComponent,
    FormsModule
  ],
  templateUrl: './request-payment-page.component.html',
  styleUrl: './request-payment-page.component.css'
})
export class RequestPaymentPage {
  store = inject(AppStore);

  recipientType: any = 'COMPANY';
  protected isModalInvoiceOpen = false;
  protected isBeneficiaryModalOpen = false;
  protected activeTab: string = 'beneficiary';
  protected modalContent: string = '';
  modalTitle: string = '';


  setActiveTab(tab: string) {
    this.activeTab = tab;
  }

  openInvoiceModal(): void {
    this.isModalInvoiceOpen = true;
    this.modalContent = 'invoice';
    this.modalTitle = 'Select an invoice';
  }

  openBeneficiaryModal(): void {
    this.isBeneficiaryModalOpen = true;
    this.modalContent = 'beneficiary';
    this.modalTitle = 'Select a beneficiary';
  }

  closeModal(): void {
    this.isModalInvoiceOpen = false;
    this.isBeneficiaryModalOpen = false;
    this.modalContent = '';
  }

  onSubmit() {
    const selectedCompany = this.store.selectedCompany();
    const selectedInvoice = this.store.selectedInvoice();
    const invoiceBeneficiary = this.store.invoiceBeneficiary();

    if (!selectedCompany || !selectedInvoice || !invoiceBeneficiary) {
      console.error('Test failed: Missing required data');
      return;
    }

    const paymentRequest: PaymentRequest = {
      companyId: selectedCompany.id,
      invoiceId: selectedInvoice.invoiceId.toString(),
      invoiceName: selectedInvoice.invoiceTitle,
      amountInvoice: selectedInvoice.totalIncludingTax,
      recipientId: invoiceBeneficiary.id.toString(),
      recipientType: this.recipientType || 'COMPANY',
      recipientName: invoiceBeneficiary.name,
      recipientEmail: invoiceBeneficiary.email,
      senderId: selectedCompany.id,
      recipientExternalEmail: invoiceBeneficiary.email || ''
    };

    this.store.createPaymentRequest(paymentRequest);
  }
}
