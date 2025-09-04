import {inject, Injectable} from '@angular/core';
import {firstValueFrom} from 'rxjs';
import {Router} from '@angular/router';
import {SignUpRequest} from '../models/signUpRequest';
import {ApiService} from '../../shared/api/services/api.service';
import {LoginRequest} from '../models/loginRequest';
import {HttpResponse} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private router = inject(Router);
  private apiService = inject(ApiService);

  constructor() { }

  async register(data: SignUpRequest): Promise<any> {
    try {
      localStorage.setItem('userRegistrationEmail', data.email);
      const response = await firstValueFrom(this.apiService.postData("v1/auth/register", data));
      await this.router.navigate(['/validation-account']);
      return response;
    } catch (error) {
      return await Promise.reject(error);
    }
  }

  async validateAccount(token: string): Promise<any> {
    try {
      return await firstValueFrom(this.apiService.postData("v1/auth/confirm-account", {token}))
    } catch (error) {
      return await Promise.reject(error);
    }
  }

  async checkIfAccountIsValidated(): Promise<any> {
    try {
      const email = localStorage.getItem('userRegistrationEmail');
      const response = await firstValueFrom(this.apiService.getData(`v1/auth/is-verified-account?email=${email}`));
      if (response.body.isVerified === true) {
        localStorage.removeItem('userRegistrationEmail');
        return true
      }
      if (response.body.isVerified === false) {
        return false
      }
    } catch (error) {
      return await Promise.reject(error);
    }
  }

  async login(data: LoginRequest): Promise<HttpResponse<any>> {
    try {
      const response = await firstValueFrom(this.apiService.postData("v1/auth/authenticate", data));
      localStorage.setItem('access_token', response.body.access_token);
      await this.router.navigate(['/']);
      return response;
    } catch (error) {
      return await Promise.reject(error);
    }
  }

  async logout(): Promise<void> {
    localStorage.removeItem('access_token');
    localStorage.removeItem('store');
    const response = await firstValueFrom(this.apiService.postData("v1/auth/success/logout", {}));
    await this.router.navigate(['/landing-page']);
    window.location.reload();
    return response.body;
  }

  async isAuthenticated(): Promise<boolean> {
    try {
      const response = await firstValueFrom(this.apiService.getData("v1/auth/isconnected"));
      return response.body;
    } catch (error) {
      console.error('Error during authentication check:', error);
      return false;
    }
  }
}
