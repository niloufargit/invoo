import { Component } from '@angular/core';
import {FormGroup, ReactiveFormsModule} from '@angular/forms';
import {ForgotPasswordFormComponent} from './forgot-password-form/forgot-password-form.component';

@Component({
  selector: 'app-forgot-password-page',
  imports: [
    ReactiveFormsModule,
    ForgotPasswordFormComponent
  ],
  templateUrl: './forgot-password-page.component.html',
  styleUrl: './forgot-password-page.component.css'
})
export class ForgotPasswordPageComponent {
  loginForm!: FormGroup;

  onSubmit() {

  }
}
