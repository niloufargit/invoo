import {Component, inject, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {AppStore} from '../../../../../../shared/store/app.store';
import {ActivatedRoute} from '@angular/router';
import {GetCompanyByIdResponse} from '../../../../../companies/models/company.models';
import {User} from '../../../../models/user.model';
import {CommonModule} from '@angular/common';
import { Router } from '@angular/router';


@Component({
  selector: 'app-update-user-form',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './update-user-form.component.html',
  styleUrl: './update-user-form.component.css'
})
export class UpdateUserFormComponent implements OnInit{
  @Input() userId?: string;

  private router = inject(Router);
  private fb= inject(FormBuilder);
  protected store= inject(AppStore);
  private route = inject(ActivatedRoute);

  private currentUser : any ;
  protected updatedUser: User | null = null;

  ngOnInit(): void {
      this.updatedUser = this.store.user();
      this.initFormGroupWithUpdatedUser();
  }

  userForm: FormGroup = this.fb.group({
    firstname: ['', [Validators.required]],
    lastname: ['', [Validators.required]],
    email: ['', [Validators.required, Validators.email]],
  });

  onSubmit() {
    if (this.userForm.valid && this.updatedUser) {
      if (this.hasUserChanged()) {
        const updatedData: User = {
          ...this.updatedUser,
          firstname: this.userForm.value.firstname,
          lastname: this.userForm.value.lastname,
          email: this.userForm.value.email,
        };

        this.store.updateUser(updatedData);

        console.log('User updated successfully!');
      } else {
        console.log('No changes detected, skipping update.');
      }
    }
  }

  private initFormGroupWithUpdatedUser() {
    if (this.updatedUser) {
      this.userForm.patchValue({
        id: this.updatedUser.id,
        firstname: this.updatedUser.firstname,
        lastname: this.updatedUser.lastname,
        email: this.updatedUser.email,
      });
    }
  }

  private hasUserChanged() {
    if (!this.updatedUser) return false;

    const formValue = this.userForm.value;

    return (
      formValue.firstname !== this.updatedUser.firstname ||
      formValue.lastname !== this.updatedUser.lastname ||
      formValue.email !== this.updatedUser.email
    );
  }
}

