import {Component, inject, input, output, signal} from '@angular/core';
import {TableNewProductComponent} from '../table-new-product/table-new-product.component';
import {ModalComponent} from '../../../../../../../../shared/components/modal/modal.component';
import {
  CardComponentComponent
} from '../../../../../../../../shared/components/cards/card-component/card-component.component';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {AppStore} from '../../../../../../../../shared/store/app.store';
import {ProductSaving} from '../../../../../../../models/product.model';

@Component({
  selector: 'app-form-new-product',
  imports: [
    TableNewProductComponent,
    ModalComponent,
    CardComponentComponent,
    ReactiveFormsModule,
    FormsModule
  ],
  templateUrl: './form-new-product.component.html',
  styleUrl: './form-new-product.component.css'
})
export class FormNewProductComponent {

  addNewProduct = input<boolean>(false);
  closeModalNewProduct = output<boolean>();

  private fb= inject(FormBuilder);
  protected store= inject(AppStore);

  checkboxCreateCategory = signal<boolean>(true);
  checkboxSelectCategory = signal<boolean>(false);

  productForm: FormGroup = this.fb.group({
    id: [''],
    name: ['', [Validators.required]],
    description: [''],
    barcode: ['', [Validators.required]],
    reference: ['', [Validators.required]],
    htPrice: ['', [Validators.required]],
    vatRate: ['', [Validators.required]],
    idCategory: [''],
    categoryName: ['', [Validators.required]],
    categoryDescription: [''],
    categoryReference: ['', [Validators.required]],
    idCatalog: ['']
  });

  closeAddModal() {
    this.closeModalNewProduct.emit(true)
  }

  onSubmit() {


    if (this.productForm.value.idCategory && this.checkboxSelectCategory()) {
      const cts = this.store.getCategoriesCreatedCatalog()
        .find(cat => cat.id === Number(this.productForm.value.idCategory));
      this.productForm.patchValue({
        categoryName: cts?.name,
        categoryDescription: cts?.description,
        categoryReference: cts?.reference
      });
    }

    if (this.productForm.invalid) return;
    const formValue = this.productForm.value;
    const payload = {
      idCategory: this.checkboxCreateCategory() ? null : formValue.idCategory,
      name: formValue.categoryName,
      description: formValue.categoryDescription,
      reference: formValue.categoryReference,
      idCatalog: Number(this.store.createdCatalog()?.id),
      products: [
        {
          name: formValue.name,
          description: formValue.description,
          barcode: formValue.barcode,
          reference: formValue.reference,
          htPrice: formValue.htPrice,
          vatRate: formValue.vatRate,
          idCatalog: Number(this.store.createdCatalog()?.id),
          idCategory: this.checkboxCreateCategory() ? null : formValue.idCategory
        }
      ]
    } as unknown as ProductSaving;

    this.store.addProduct(payload)
      .then((response) => {
          this.productForm.reset();
          this.store.getAllCatalogs();
      }).catch((error) => {
        console.error('Error adding product:', error);
      });

  }

  onCheckboxChange() {
    this.checkboxCreateCategory.set(!this.checkboxCreateCategory());
    this.checkboxSelectCategory.set(!this.checkboxSelectCategory());
  }
}
