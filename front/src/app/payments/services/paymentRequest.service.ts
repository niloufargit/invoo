import {inject, Injectable} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {firstValueFrom} from 'rxjs';
import {ApiService} from '../../shared/api/services/api.service';
import {PaymentRequest, PaymentRequestGateway, PaymentResponse} from '../models/paymentRequest.model';

@Injectable({
  providedIn: 'root'
})
export class PaymentRequestService implements PaymentRequestGateway {
  private apiService = inject(ApiService);
  private apiUrl = 'v1/payment';

  async provideCheckoutUrlSession(request: PaymentRequest): Promise<HttpResponse<PaymentResponse>> {
    try {
      return await firstValueFrom(
        this.apiService.postData(`${this.apiUrl}/request`, request)
      );
    } catch (error) {
      return Promise.reject(error);
    }
  }

  async getAllPaymentRequestsByCompanyId(id: string | undefined): Promise<HttpResponse<PaymentRequest[]>> {
    try {
      return await firstValueFrom(
        this.apiService.getData(`${this.apiUrl}/company/${id}`)
      );
    } catch (error) {
      return Promise.reject(error);
    }
  }
  async getAllPaymentRequestsReceivedByCompanyId(id: string | undefined): Promise<HttpResponse<PaymentRequest[]>> {
    try {
      return await firstValueFrom(
        this.apiService.getData(`${this.apiUrl}/received/${id}`)
      );
    } catch (error) {
      return Promise.reject(error);
    }
  }

}
