import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {AuthService} from '../../../../services/auth.service';
import {confirmEqualValidator} from '../../../../../shared/validators/confirm-equal-validator';
import {AsyncPipe} from '@angular/common';
import {map, Observable} from 'rxjs';

@Component({
  selector: 'app-register-form',
  imports: [
    ReactiveFormsModule,
    AsyncPipe
  ],
  templateUrl: './register-form.component.html',
  styleUrl: './register-form.component.css'
})
export class RegisterFormComponent implements OnInit{

  private fb= inject(FormBuilder);
  private authService= inject(AuthService);
  showPasswordError$!: Observable<boolean>;
  protected isLoading = false;
  protected isErrorLogin = false;

  registerForm: FormGroup = this.fb.group({
    firstname: ['', [Validators.required]],
    lastname: ['', [Validators.required]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [
      Validators.required,
      Validators.minLength(8),
      Validators.pattern(/(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*(),.?":{}|<>])/)
    ]],
    confirmPassword: ['', [Validators.required]],
    terms: [false, [Validators.required, Validators.requiredTrue]]
  }, {
    validators: [confirmEqualValidator('password','confirmPassword')],
    updateOn: 'blur'
  });

  ngOnInit(): void {
    this.initShowPasswordError();
  }

  private initShowPasswordError(): void {
    this.showPasswordError$ = this.registerForm.statusChanges.pipe(
      map(status =>
        status === 'INVALID' &&
        this.registerForm.value.password &&
        this.registerForm.value.confirmPassword &&
        this.registerForm.hasError('confirmEqual')
      )
    );

  }

  async onSubmit(): Promise<void> {
    if (this.registerForm.valid) {
      this.isLoading = true;
      await this.authService.register(this.registerForm.value)
        .then(() => {
          this.isLoading = false;
        })
        .catch(() => {
            this.isLoading = false;
            this.isErrorLogin = true;
          }
        );
    } else {
      this.registerForm.markAllAsTouched();
    }
  }

}
