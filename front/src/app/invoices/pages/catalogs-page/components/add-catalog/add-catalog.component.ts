import {Component, EventEmitter, inject, OnInit, Output} from '@angular/core';
import {ModalComponent} from '../../../../../shared/components/modal/modal.component';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {NgIf} from '@angular/common';
import {AppStore} from '../../../../../shared/store/app.store';
import {Catalog, CatalogWithCategories} from '../../../../models/catalog.model';

@Component({
  selector: 'app-add-catalog',
  imports: [ FormsModule, ReactiveFormsModule, NgIf],
  templateUrl: './add-catalog.component.html',
  standalone: true,
  styleUrl: './add-catalog.component.css'
})
export class AddCatalogComponent implements OnInit{
 isOpen: boolean = false;
  @Output() closeMyModal = new EventEmitter<boolean>();

  catalogForm!: FormGroup;

  store = inject(AppStore);

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.catalogForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
    });
  }
  async onSubmit() {
    if (this.catalogForm.invalid) return;

    const idCompany = this.store.selectedCompany()?.id;

    const newCatalogue: Catalog = {
      name: this.catalogForm.get('name')?.value,
      description:  this.catalogForm.get('description')?.value,
      idCompany} as Catalog;

    this.store.addCatalog(newCatalogue)

  }

  openModal() {
    this.isOpen = true;
  }

  closeModal() {
    this.isOpen = false;
  }

  onClick(){
    this.closeMyModal.emit(false);
  }
}
