import { Component } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {LoginFormComponent} from '../login-page/components/login-form/login-form.component';
import {RegisterFormComponent} from './components/register-form/register-form.component';

@Component({
  selector: 'app-register-page',
  imports: [
    RegisterFormComponent
  ],
  templateUrl: './register-page.component.html',
  styleUrl: './register-page.component.css'
})
export class RegisterPage {

}
