import {GetAllCompaniesResponse, GetCompanyByIdResponse} from '../../../settings/companies/models/company.models';
import {User} from '../../../settings/user/models/user.model';
import {CatalogWithCategories, CategoryDto, GetAllCatalogs} from '../../../invoices/models/catalog.model';
import {GetAllProducts, Product} from '../../../invoices/models/product.model';
import {Beneficiary} from '../../../payments/models/beneficiary.model';
import {InvoiceAPI} from '../../../invoices/models/invoice.model';
import {PaymentRequestResponse, PaymentResponse} from '../../../payments/models/paymentRequest.model';
import {ProductStatistic} from '../init-data/statistic.selector';

export type AppState = {
  user: User | null;
  errorLogin: boolean;
  companies: GetAllCompaniesResponse;
  selectedCompany: GetCompanyByIdResponse | null;
  currentActiveName: string;
  currentActiveId: string;
  catalogs: GetAllCatalogs;
  selectedCatalog: CatalogWithCategories | null;
  selectedCategoryId: number;
  products: GetAllProducts;
  beneficiaries: any;
  isLoaded: boolean;
  errorLoading: boolean;
  closeModal: boolean;
  isBeneficiaryAdded: boolean;
  beneficiaryFilter: string | undefined;
  invoices: Array<InvoiceAPI>;
  invoiceBeneficiary: Beneficiary | undefined;
  invoiceProducts: Array<Product> | undefined;
  paymentRequests: Array<PaymentResponse> | undefined;
  selectedInvoice: InvoiceAPI | undefined;
  createdCatalog: CategoryDto | undefined;
  createdProducts: GetAllProducts | undefined,
  paymentRequestsReceived: Array<PaymentRequestResponse> | undefined;
  productStatistics: Array<ProductStatistic> | undefined;
}
