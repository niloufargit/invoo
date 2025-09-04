import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {AppStore} from '../../../../../../shared/store/app.store';
import {Catalog} from '../../../../../models/catalog.model';
import {AddNewProductComponent} from './add-new-product/add-new-product.component';
import {Router} from '@angular/router';

@Component({
  selector: 'app-add-new-catalog',
  imports: [

    ReactiveFormsModule,
    AddNewProductComponent
  ],
  templateUrl: './add-new-catalog.component.html',
  styleUrl: './add-new-catalog.component.css'
})
export class AddNewCatalogComponent implements OnInit{

  isOpen: boolean = false;

  router = inject(Router);

  fb = inject(FormBuilder);
  store = inject(AppStore);
  catalogForm: FormGroup = this.fb.group({
    name: ['', Validators.required],
    description: ['', Validators.required],
    reference: ['', Validators.required],
  });


  ngOnInit(): void {
    if (this.store.createdCatalog()) {
      this.catalogForm.patchValue({
        name: this.store.createdCatalog()?.name,
        description: this.store.createdCatalog()?.description,
        reference: this.store.createdCatalog()?.reference
      })
    }
  }

  async onSubmit() {
    if (this.catalogForm.invalid) return;
    const idCompany = this.store.selectedCompany()?.id;
    const newCatalogue: Catalog = {
      ...this.catalogForm.value,
      idCompany} as Catalog;
    this.store.addCatalog(newCatalogue)
  }

  updateCatalog() {
    if (this.catalogForm.invalid) return;
    const id = this.store.createdCatalog()?.id;
    const idCompany = this.store.selectedCompany()?.id;
    const updatedCatalog: Catalog = {
      ...this.catalogForm.value,
      idCompany,
      id} as Catalog;
    this.store.updateCatalog(updatedCatalog);
  }

  confirmAllCatalog() {
    this.store.clearCreatedCatalog();
    this.router.navigate(['/catalogs']);
  }

  revokeCatalog() {
    this.store.deleteCatalog(this.store.createdCatalog()?.id);
    this.router.navigate(['/catalogs']);
    this.store.getAllCatalogs();
  }
}
