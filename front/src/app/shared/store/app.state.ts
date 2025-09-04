import {AppState} from './models/store.models';
import {User} from '../../settings/user/models/user.model';
import {CatalogWithCategories} from '../../invoices/models/catalog.model';

export const initialState: AppState = {
  user: {} as User,
  errorLogin: false,
  companies: [],
  selectedCompany: null,
  currentActiveName: '',
  currentActiveId: '',
  catalogs: [],
  selectedCatalog: {} as CatalogWithCategories,
  products: [],
  beneficiaries: undefined,
  isLoaded: false,
  errorLoading: false,
  isBeneficiaryAdded: false,
  beneficiaryFilter: undefined,
  invoiceBeneficiary: undefined,
  invoiceProducts: undefined,
  invoices: [],
  paymentRequests: undefined,
  selectedCategoryId: 0,
  closeModal: false,
  createdCatalog: undefined,
  createdProducts: undefined,
  selectedInvoice: undefined,
  paymentRequestsReceived: undefined,
  productStatistics: undefined
}
