import {inject, Injectable} from '@angular/core';
import {firstValueFrom} from 'rxjs';
import {ApiService} from '../../../shared/api/services/api.service';
import {Company} from '../../companies/models/company.models';
import {HttpResponse} from '@angular/common/http';
import {User} from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiService = inject(ApiService);

  constructor() { }

  async getCurrentUser(): Promise<any> {
    try {
      const response = await firstValueFrom(this.apiService.getData("v1/users/me"));
      return response;
    } catch (error) {
      return await Promise.reject(error);
    }
  }

  async updateUser(id: string, payload: User): Promise<HttpResponse<any>> {
    try {
      return await firstValueFrom(this.apiService.putData(`v1/users/update/${id}`, payload));
    } catch (error) {
      return await Promise.reject(error);
    }
  }
}
