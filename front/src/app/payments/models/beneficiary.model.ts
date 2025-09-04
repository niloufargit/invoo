import {Address} from '../../shared/address/models/address.model';
import {CompanyType} from '../../settings/companies/models/company.models';

export type Beneficiary = {
  id: number,
  name: string,
  sirenNumber: number,
  phoneNumber: number,
  address: Address;
  email: string,
  logo: any,
  companyType: CompanyType
}


export interface BeneficiaryGateway {
  addBeneficiary(email: string | null, idCompany: string | null, idSelectedCompany: string | undefined): Promise<any>;
  getAllBeneficiariesByIdCompany(idCompany: string | undefined): Promise<any>;
  searchIndividualBeneficiary(emailBeneficiary: string): Promise<any>;
  searchProfessionalBeneficiary(name: string, sirenNumber: string): Promise<any>;
}
