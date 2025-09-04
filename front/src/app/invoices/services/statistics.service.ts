import {inject, Injectable} from '@angular/core';
import {ApiService} from '../../shared/api/services/api.service';
import {InvoiceContent} from '../models/invoice.model';
import {firstValueFrom} from 'rxjs';
import {HttpResponse} from '@angular/common/http';
import {InvoiceGateway} from '../../payments/models/invoice.model';

@Injectable({
  providedIn: 'root'
})
export class StatisticsService {

  private apiService = inject(ApiService);

  // v1/invoices/statistics/products/{companyId}

  async getProductsStatistics(companyId: string | undefined): Promise<HttpResponse<any>> {
    try {
      return await firstValueFrom(
        this.apiService.getData(`v1/invoices/statistics/products/${companyId}`)
      );
    } catch (error) {
      return Promise.reject(error);
    }
  }

}
