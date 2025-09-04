import {Product} from './product.model';
import {Beneficiary} from '../../payments/models/beneficiary.model';

export type InvoiceAPI = {
  id: number;
  idCompany: string;
  idBeneficiary: string;
  invoiceId: number;
  invoiceNumber: string;
  invoiceTitle: string;
  totalIncludingTax: number;
}

export type InvoiceContent = {
  invoiceNumber: string;
  companyId: number;
  beneficiary: Beneficiary;
  invoiceTitle: string;
  deliveryDate: string;
  products: Product[];
  price: Price;
};

export type Price = {
  unitPrice: number;
  totalExcludingTax: number,
  totalIncludingTax: number,
}
