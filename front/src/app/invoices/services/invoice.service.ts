import {inject, Injectable} from '@angular/core';
import {ApiService} from '../../shared/api/services/api.service';
import {InvoiceContent} from '../models/invoice.model';
import {firstValueFrom} from 'rxjs';
import {HttpResponse} from '@angular/common/http';
import {InvoiceGateway} from '../../payments/models/invoice.model';

@Injectable({
  providedIn: 'root'
})
export class InvoiceService implements InvoiceGateway{
  private apiService = inject(ApiService);
  private apiUrl = 'v1/invoices';

  async generateInvoice(invoice: InvoiceContent) {
    return await firstValueFrom(this.apiService.postData(`${this.apiUrl}/create`, invoice));
  }

  async getInvoiceContentById(id: number | undefined): Promise<HttpResponse<any>> {
    return await firstValueFrom(this.apiService.getData(`${this.apiUrl}/${id}`));
  }

  async getAllInvoicesByIdCompany(id: string | undefined): Promise<HttpResponse<any>> {
    try {
      return await firstValueFrom(
        this.apiService.getData(`${this.apiUrl}/record/${id}`)
      );
    } catch (error) {
      return Promise.reject(error);
    }
  }

  async downloadInvoicePdf(id: number | undefined) {
    const response = await firstValueFrom(
      this.apiService.getData(`${this.apiUrl}/download/${id}`, { responseType: 'blob' })
    );
    const blob = new Blob([response.body], { type: 'application/pdf' });
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', `invoice-${id}.pdf`);
    document.body.appendChild(link);
    link.click();
    link.remove();
  }

}
