import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from '../../../../environments/environment.development';


@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private http = inject(HttpClient)
  private baseUrl = environment.apiURL;
  constructor() { }

  getData(endpoint: string, options?: any): Observable<HttpResponse<any>> {
    return this.http.get<any>(`${this.baseUrl}/${endpoint}`, { observe: 'response', ...options }) as Observable<HttpResponse<any>>;
  }

  postData(endpoint: string, data: any): Observable<HttpResponse<any>> {
    return this.http.post<any>(`${this.baseUrl}/${endpoint}`, data, { observe: 'response' });
  }

  putData(endpoint: string, data: any): Observable<HttpResponse<any>> {
    return this.http.put(`${this.baseUrl}/${endpoint}`, data, { observe: 'response' });
  }

  deleteData(endpoint: string): Observable<HttpResponse<any>> {
    return this.http.delete(`${this.baseUrl}/${endpoint}`, { observe: 'response' });
  }}
