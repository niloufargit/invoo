import {inject, Injectable} from '@angular/core';
import {ApiService} from '../../shared/api/services/api.service';
import {Product, ProductSaving} from '../models/product.model';
import {firstValueFrom} from 'rxjs';
import {HttpResponse} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private apiService = inject(ApiService);

  async createProduct(payload: ProductSaving): Promise<HttpResponse<any>> {
    try {
      return await firstValueFrom(this.apiService.postData("v1/products", payload));
    } catch (error) {
      return await Promise.reject(error);
    }
  }

  async getProductById(id: string): Promise<HttpResponse<any>> {
    try {
      return await firstValueFrom(this.apiService.getData(`/v1/products/${id}`));
    } catch (error) {
      return await Promise.reject(error);
    }
  }

  async getProducts(): Promise<HttpResponse<any>> {
    try {
      return await firstValueFrom(this.apiService.getData("/v1/products"));
    } catch (error) {
      return await Promise.reject(error);
    }
  }

  async updateProduct(id: string, payload: Product): Promise<HttpResponse<any>> {
    try {
      return await firstValueFrom(this.apiService.putData(`v1/products/${id}`, payload));
    } catch (error) {
      return await Promise.reject(error);
    }
  }

  async deleteProductById(id: string): Promise<void> {
    try {
      await firstValueFrom(this.apiService.deleteData(`v1/products/${id}`));
    } catch (error) {
      return await Promise.reject(error);
    }
  }

  async getProductsByCatalogId(catalogId: string): Promise<HttpResponse<any>> {
    try {
      return await firstValueFrom(this.apiService.getData(`v1/products/catalog/${catalogId}`));
    } catch (error) {
      return await Promise.reject(error);
    }
  }

  async getProductsByCategoryId(categoryId: string): Promise<HttpResponse<any>> {
    try {
      return await firstValueFrom(this.apiService.getData(`v1/products/category/${categoryId}`));
    } catch (error) {
      return await Promise.reject(error);
    }
  }

  async deleteProduct(productId: string) {
    try {
      return await firstValueFrom(this.apiService.deleteData(`v1/products/${productId}`));
    } catch (error) {
      return await Promise.reject(error);
    }
  }
}
