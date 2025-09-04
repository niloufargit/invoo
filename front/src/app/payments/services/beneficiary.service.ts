import {inject, Injectable} from '@angular/core';
import { HttpResponse} from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import {BeneficiaryGateway} from '../models/beneficiary.model';
import {ApiService} from '../../shared/api/services/api.service';

@Injectable({
  providedIn: 'root'
})
export class BeneficiaryService implements BeneficiaryGateway {

  private apiService = inject(ApiService);

  private apiUrl = 'beneficiaries';

  async addBeneficiary(email: string | null, idCompany: string | null, idSelectedCompany: string | undefined): Promise<HttpResponse<any>> {
    const body = {email, idCompany, idSelectedCompany};
    try {
      return await firstValueFrom(this.apiService.postData(this.apiUrl + '/add', body))
    } catch (error) {
      return Promise.reject(error);
    }
  }

  async getAllBeneficiariesByIdCompany(idCompany: string | undefined): Promise<HttpResponse<any>> {
    try {
      return await firstValueFrom(this.apiService.getData(this.apiUrl + `/${idCompany}`))
    } catch (error) {
      return Promise.reject(error);
    }
  }

  async searchIndividualBeneficiary(emailBeneficiary: string): Promise<HttpResponse<any>> {
    try {
      return await firstValueFrom(this.apiService.postData(`${this.apiUrl}search?emailBeneficiary=${emailBeneficiary}`, {})
        .pipe(
          map(response => response),
          catchError(error => {
            throw error.error;
          })
        ));
    } catch (error) {
      return Promise.reject(error);
    }
  }

  async searchProfessionalBeneficiary(name: string, sirenNumber?: string): Promise<HttpResponse<any>> {
    try {
      return await firstValueFrom(this.apiService.postData(`v1/invoices/companies/search?name=${name}&sirenNumber=${sirenNumber}`, {})
        .pipe(
          map(response => response),
          catchError(error => {
            throw error.error;
          })
        ));
    } catch (error) {
      return Promise.reject(error);
    }
  }

  async deleteBeneficiary(id: string, idCompany: string | undefined ): Promise<HttpResponse<any>> {
    try {
      return firstValueFrom(this.apiService.deleteData(`${this.apiUrl}/${id}/${idCompany}`))
    } catch (error) {
      return Promise.reject(error);
    }

  }
}
