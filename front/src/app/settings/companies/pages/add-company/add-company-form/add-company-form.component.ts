import {Component, inject, input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {AppStore} from '../../../../../shared/store/app.store';
import {GetCompanyByIdResponse} from '../../../models/company.models';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-add-company-form',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './add-company-form.component.html',
  styleUrl: './add-company-form.component.css'
})
export class AddCompanyFormComponent implements OnInit{

  private fb= inject(FormBuilder);
  protected store= inject(AppStore);
  private route = inject(ActivatedRoute);

  updatedCompany!: GetCompanyByIdResponse;

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const id =  params['data'];
      this.updatedCompany = this.store.companies()?.filter(company => company.id === id)[0];
    });

    this.initFormGroupWithUpdatedCompany();
  }

  companyForm: FormGroup = this.fb.group({
    id: [''],
    email: ['', [Validators.required, Validators.email]],
    name: ['', [Validators.required]],
    sirenNumber: ['', [Validators.required]],
    numberStreet: ['', [Validators.required]],
    street: ['', [Validators.required]],
    city: ['', [Validators.required]],
    zipCode: ['', [Validators.required]],
    country: ['', [Validators.required]],
    phoneNumber: ['', [Validators.required]],
    companyType: ['', [Validators.required]],
  });

  onSubmit() {
    if (this.companyForm.valid) {
      if (this.updatedCompany) {
        this.store.updateCompany(this.updatedCompany.id, this.companyForm.value);
      } else {
        this.store.createCompany(this.companyForm.value)
      }
    }
  }

  initFormGroupWithUpdatedCompany() {
    if (this.updatedCompany) {
      this.companyForm.patchValue({
        id: this.updatedCompany.id,
        email: this.updatedCompany.email,
        name: this.updatedCompany.name,
        sirenNumber: this.updatedCompany.sirenNumber,
        numberStreet: this.updatedCompany.address.streetNumber,
        street: this.updatedCompany.address.street,
        city: this.updatedCompany.address.city,
        zipCode: this.updatedCompany.address.zipCode,
        country: this.updatedCompany.address.country,
        phoneNumber: this.updatedCompany.phoneNumber,
        companyType: this.updatedCompany.companyType
      });
    }
  }

}
