import {Component, inject, output} from '@angular/core';
import {
	CardComponentComponent
} from "../../../../../../../../shared/components/cards/card-component/card-component.component";
import {EditProductComponent} from "../../../../products-table/edit-product/edit-product.component";
import {ModalComponent} from "../../../../../../../../shared/components/modal/modal.component";
import {ProductApi} from '../../../../../../../models/product.model';
import {AppStore} from '../../../../../../../../shared/store/app.store';

@Component({
  selector: 'app-table-new-product',
	imports: [
		CardComponentComponent,
		EditProductComponent,
		ModalComponent
	],
  templateUrl: './table-new-product.component.html',
  styleUrl: './table-new-product.component.css'
})
export class TableNewProductComponent {

  selectedProduct(): ProductApi | null {
    throw new Error('Method not implemented.');
  }
  openDeleteProduct(_t19: ProductApi) {
    throw new Error('Method not implemented.');
  }

  store = inject(AppStore);
  openEditProduct: any;

  openEditProductModal(product: any) {

  }

  closeEditProductModal() {

  }

}
