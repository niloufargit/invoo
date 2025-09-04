import {Component, effect, inject, OnInit, signal} from '@angular/core';
import {AppStore} from '../../../../../shared/store/app.store';
import {FormsModule} from '@angular/forms';
import {CardComponentComponent} from '../../../../../shared/components/cards/card-component/card-component.component';
import {ModalComponent} from '../../../../../shared/components/modal/modal.component';
import {EditProductComponent} from './edit-product/edit-product.component';
import {ProductApi} from '../../../../models/product.model';

@Component({
  selector: 'app-products-table',
  imports: [
    FormsModule,
    CardComponentComponent,
    ModalComponent,
    EditProductComponent
  ],
  templateUrl: './products-table.component.html',
  styleUrl: './products-table.component.css'
})
export class ProductsTableComponent implements OnInit {

  protected store = inject(AppStore);
  products = this.store.products();
  searchKey = signal<string>('');
  protected openEditProduct = false;
  protected selectedProduct = signal<ProductApi | null>(null);

  productDeletedId = signal<string>('');

  constructor() {
    effect(() => {
      this.products = this.store.filteredProducts(this.searchKey(), this.store.selectedCategoryId());
    });
    effect(() => {
      this.openEditProduct = !this.store.closeModal()
    });
  }

  ngOnInit(): void {
    this.products = this.store.products();
  }



  protected openEditProductModal(editedProduct: ProductApi) {
    this.openEditProduct = !this.openEditProduct;
    this.selectedProduct.set(editedProduct);
    this.store.openedModal();
  }

  closeEditProductModal() {
    this.openEditProduct = !this.openEditProduct;
  }

  openDeleteProduct(product: ProductApi) {
    this.productDeletedId.set(product.id);
  }

  closeDeleteProductModal() {
    this.productDeletedId.set('');
  }

  deleteProduct() {
    this.store.deleteProduct(this.productDeletedId());
    this.productDeletedId.set('');
  }
}
