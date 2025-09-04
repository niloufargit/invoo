import {Component, inject, input, OnInit} from '@angular/core';
import {ProductApi} from '../../../../../models/product.model';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {AppStore} from '../../../../../../shared/store/app.store';

@Component({
  selector: 'app-edit-product',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './edit-product.component.html',
  styleUrl: './edit-product.component.css'
})
export class EditProductComponent implements OnInit{

  ngOnInit(): void {
    this.initFormGroup();
  }

  editedProduct = input<ProductApi | null>(null);

  private fb= inject(FormBuilder);
  protected store= inject(AppStore);


  productForm: FormGroup = this.fb.group({
    id: [''],
    name: ['', [Validators.required]],
    description: [''],
    barcode: ['', [Validators.required]],
    reference: ['', [Validators.required]],
    htPrice: ['', [Validators.required]],
    vatRate: ['', [Validators.required]],
    idCategory: [''],
    idCatalog: ['']
  });

  initFormGroup() {
    if (this.editedProduct()) {
      this.productForm.patchValue({
        id: this.editedProduct()?.id,
        name: this.editedProduct()?.name,
        description: this.editedProduct()?.description,
        barcode: this.editedProduct()?.barcode,
        reference: this.editedProduct()?.reference,
        htPrice: this.editedProduct()?.htPrice,
        vatRate: this.editedProduct()?.vatRate,
        idCategory: this.editedProduct()?.idCategory,
        idCatalog: this.editedProduct()?.idCatalog
      });
    }
  }


  onSubmit() {
    if (this.productForm.valid) {
      const productData = this.productForm.value;
      this.store.editProduct(productData);
      this.store.closedModal();
    }

  }
}
