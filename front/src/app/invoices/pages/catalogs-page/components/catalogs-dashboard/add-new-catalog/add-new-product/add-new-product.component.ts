import {Component, signal} from '@angular/core';
import {FormNewProductComponent} from './form-new-product/form-new-product.component';

@Component({
  selector: 'app-add-new-product',
  imports: [
    FormNewProductComponent
  ],
  templateUrl: './add-new-product.component.html',
  styleUrl: './add-new-product.component.css'
})
export class AddNewProductComponent {

  newProduct = signal<boolean>(false);

  addNewProduct() {
    this.newProduct.set(true);
  }

  closeAddProductModal($event: boolean) {
    this.newProduct.set(false);
  }
}
