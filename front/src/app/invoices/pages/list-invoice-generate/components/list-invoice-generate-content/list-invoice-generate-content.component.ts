import {Component, inject} from '@angular/core';
import {AppStore} from '../../../../../shared/store/app.store';
import {Router} from '@angular/router';
import {ModalComponent} from '../../../../../shared/components/modal/modal.component';
import {CardComponentComponent} from '../../../../../shared/components/cards/card-component/card-component.component';
import {FormsModule} from '@angular/forms';
import {DomSanitizer, SafeHtml} from '@angular/platform-browser';

@Component({
  selector: 'app-list-invoice-generate-content',
  imports: [
    ModalComponent,
    CardComponentComponent,
    FormsModule
  ],
  templateUrl: './list-invoice-generate-content.component.html',
  styleUrl: './list-invoice-generate-content.component.css'
})
export class ListInvoiceGenerateContentComponent {
  protected store = inject(AppStore);
  protected router = inject(Router);
  private sanitizer = inject(DomSanitizer)
  invoiceDetailsContent: any;
  searchKeyList: any;
  protected invoiceDetailsModal = false;

  toggleInvoiceDetailsModal() {
    this.invoiceDetailsModal = !this.invoiceDetailsModal;
  }

  async getInvoiceContent(id: number | undefined) {
    this.toggleInvoiceDetailsModal()
    this.invoiceDetailsContent = await this.store.getInvoiceContentById(id)
    this.invoiceDetailsContent = this.sanitizer.bypassSecurityTrustHtml(this.invoiceDetailsContent)
  }

  protected readonly Object = Object;
}
