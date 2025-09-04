import {inject, Injectable} from '@angular/core';
import {ApiService} from '../../shared/api/services/api.service';
import {Catalog, CategoryDto, GetAllCatalogs} from '../models/catalog.model';
import {firstValueFrom} from 'rxjs';
import {HttpResponse} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CatalogService {

  private apiService = inject(ApiService)

  async createCatalog(payload: Catalog): Promise<HttpResponse<any>> {
    try {
      return await firstValueFrom(this.apiService.postData("v1/catalogs", payload));
    } catch (error) {
      return await Promise.reject(error);
    }
  }

  async getCatalogById(id: string): Promise<HttpResponse<any>> {
    try {
      return await firstValueFrom(this.apiService.getData(`v1/catalogs/${id}`));
    } catch (error) {
      return await Promise.reject(error);
    }
  }

  async getCatalogsByCompanyId(id: string): Promise<HttpResponse<any>> {
    try {
      return await firstValueFrom(this.apiService.getData(`v1/catalogs/company/${id}`));
    } catch (error) {
      return await Promise.reject(error);
    }
  }

  async updateCatalog(id: number, payload: Catalog): Promise<HttpResponse<any>> {
    try {
      return await firstValueFrom(this.apiService.putData(`v1/catalogs/${id}`, payload));
    } catch (error) {
      return await Promise.reject(error);
    }
  }

  async deleteCatalog(id: number): Promise<HttpResponse<any>> {
    try {
      return await firstValueFrom(this.apiService.deleteData(`v1/catalogs/${id}`));
    } catch (error) {
      return await Promise.reject(error);
    }
  }
}
