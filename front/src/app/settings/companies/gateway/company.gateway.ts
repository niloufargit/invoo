import {Company, GetAllCompaniesResponse, GetCompanyByIdResponse} from '../models/company.models';

export interface CompaniesGateway {
  getCompanies(): Promise<GetAllCompaniesResponse>;
  getCompanyById(id: string): Promise<GetCompanyByIdResponse>;
  createCompany(payload: Company): Promise<GetCompanyByIdResponse>;
  updateCompany(id: string, payload: Company): Promise<GetCompanyByIdResponse>;
}
