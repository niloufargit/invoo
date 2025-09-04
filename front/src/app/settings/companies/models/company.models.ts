import {Address} from '../../../shared/address/models/address.model';

type CompanyAPI = {
  id: string;
  name: string;
  sirenNumber: string;
  email: string;
  numberStreet: string;
  street: string;
  city: string;
  zipCode: string;
  country: string;
  phoneNumber: string;
  type: CompanyType;
};

type CompanyAPIResponse = {
  id: string;
  name: string;
  sirenNumber: string;
  email: string;
  address: Address;
  phoneNumber: string;
  companyType: CompanyType;
};

export type CompanyType = "COMPANY" | "RCS" |  "MICRO_ENTERPRISE"
export type GetAllCompaniesResponse = CompanyAPIResponse[] | undefined;
export type GetCompanyByIdResponse = CompanyAPIResponse | undefined;
export type Company = CompanyAPI | undefined;
