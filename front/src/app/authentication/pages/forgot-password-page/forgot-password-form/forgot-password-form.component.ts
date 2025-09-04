import {Component, inject} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {AuthService} from '../../../services/auth.service';

@Component({
  selector: 'app-forgot-password-form',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './forgot-password-form.component.html',
  styleUrl: './forgot-password-form.component.css'
})
export class ForgotPasswordFormComponent {

  private fb= inject(FormBuilder);
  private authService= inject(AuthService);

  protected isLoading = false;
  protected isErrorLogin = false;

  loginForm: FormGroup = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
  });


  onSubmit() {

  }
}
