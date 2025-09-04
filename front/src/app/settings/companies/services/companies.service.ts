import {inject, Injectable} from '@angular/core';
import {ApiService} from '../../../shared/api/services/api.service';
import {Company} from '../models/company.models';
import {firstValueFrom} from 'rxjs';
import {HttpResponse} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CompaniesService {

  private apiService = inject(ApiService)

  async createCompany(payload: Company): Promise<HttpResponse<any>> {
    try {
      return await firstValueFrom(this.apiService.postData("v1/invoices/companies", payload));
    } catch (error) {
      return await Promise.reject(error);
    }
  }


  async getCompanies(): Promise<HttpResponse<any>> {
    try {
      return await firstValueFrom(this.apiService.getData("v1/invoices/companies"));
    } catch (error) {
      return await Promise.reject(error);
    }
  }

  async getCompanyById(id: string): Promise<HttpResponse<any>> {
    try {
      return await firstValueFrom(this.apiService.getData(`v1/invoices/companies/${id}`));
    } catch (error) {
      return await Promise.reject(error);
    }
  }

  async updateCompany(id: string, payload: Company): Promise<HttpResponse<any>> {
    try {
      return await firstValueFrom(this.apiService.putData(`v1/invoices/companies/update/${id}`, payload));
    } catch (error) {
      return await Promise.reject(error);
    }
  }

}
