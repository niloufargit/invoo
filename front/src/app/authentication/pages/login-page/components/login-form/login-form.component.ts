import {Component, inject} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {AuthService} from '../../../../services/auth.service';


@Component({
  selector: 'app-login-form',
  imports: [
    ReactiveFormsModule,
  ],
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.css'
})
export class LoginFormComponent {

  private fb= inject(FormBuilder);
  private authService= inject(AuthService);

  protected isLoading = false;
  protected isErrorLogin = false;

  loginForm: FormGroup = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required]],
  });

  async onSubmit() {
    if (this.loginForm.valid) {
      this.isLoading = true;
      await this.authService.login(this.loginForm.value)
        .then(() => {
          this.isLoading = false;
        })
        .catch(() => {
          this.isLoading = false;
          this.isErrorLogin = true;
        }
      );
    } else {
      this.loginForm.markAllAsTouched();
    }
  }

}
