import {Component, inject} from '@angular/core';
import {AppStore} from '../../../../../shared/store/app.store';
import {Product} from '../../../../models/product.model';
import {ModalComponent} from '../../../../../shared/components/modal/modal.component';
import {CardComponentComponent} from '../../../../../shared/components/cards/card-component/card-component.component';
import {FormsModule} from '@angular/forms';
import {InvoiceContent} from '../../../../models/invoice.model';

@Component({
  selector: 'app-generate-invoice-page-content',
  imports: [
    ModalComponent,
    CardComponentComponent,
    FormsModule
  ],
  templateUrl: './generate-invoice-page-content.component.html',
  styleUrl: './generate-invoice-page-content.component.css'
})
export class GenerateInvoicePageContentComponent {
  protected store = inject(AppStore);
  protected isProductsModalOpen = false;
  protected searchTerm: string = '';

  protected openProductsModal() {
    this.isProductsModalOpen = !this.isProductsModalOpen;
  }

  protected get filteredProducts(): Product[] | undefined {
    return this.store.products()?.filter(product =>
      product.name?.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
      product.description?.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  protected incrementQuantity(product: Product): void {
    const currentQuantity = this.store.getProductQuantity(product) || 0;
    this.store.updateProductSelectedInvoiceQuantity(product, currentQuantity + 1);
  }

  protected decrementQuantity(product: Product): void {
    const currentQuantity = this.store.getProductQuantity(product) || 0;
    if (currentQuantity > 0) {
      this.store.updateProductSelectedInvoiceQuantity(product, currentQuantity - 1);
    }
  }

  protected readonly parseFloat = parseFloat;
}
