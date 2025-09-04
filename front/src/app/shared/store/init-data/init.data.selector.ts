import {patchState, signalStoreFeature, type, withMethods} from '@ngrx/signals';
import {AppState} from '../models/store.models';
import {inject} from '@angular/core';
import {ProductService} from '../../../invoices/services/product.service';
import {CatalogService} from '../../../invoices/services/catalog.service';
import {BeneficiaryService} from '../../../payments/services/beneficiary.service';
import {InvoiceService} from '../../../invoices/services/invoice.service';
import {StatisticsService} from '../../../invoices/services/statistics.service';


export function withInitDataSelectors() {
  return signalStoreFeature(
    {
      state: type<AppState>(),
    },

    withMethods((
      {selectedCatalog, products,...store},
      productService = inject(ProductService),
      catalogService = inject(CatalogService),
      beneficiaryService = inject(BeneficiaryService),
      invoiceService = inject(InvoiceService),
      statisticService = inject(StatisticsService),
    ) => ({

      clearData() {
        patchState(store, {
          catalogs: undefined,
          selectedCatalog: undefined,
          products: undefined,
          selectedCategoryId: undefined,
          beneficiaries: undefined,
          invoices: [],
          productStatistics: undefined,
        });
      },

      iniDataCatalogs() {
        const id = store.selectedCompany()?.id;
        patchState(store, {selectedCategoryId: undefined});
        if (id) {
          catalogService.getCatalogsByCompanyId(id)
            .then((response) => {
              patchState(store, {catalogs: response.body});
              patchState(store, {selectedCatalog: (response.body.length !== 0) ? response.body[0]:undefined});
              if (selectedCatalog()?.id) {
                this._initProductsData();
              }  else {
                patchState(store, {products: undefined});
              }
            })
            .catch((error) => {
              console.error(error);
            });
          this._initBeneficiaries();
          this._initInvoices();
          this._initProductStatisticsData(id);
        }
      },

      _initProductsData() {
        const id = selectedCatalog()?.id.toString() || '0';
        if (id === '0') return;
        productService.getProductsByCatalogId(id)
          .then((response) => {
            patchState(store, {products: response.body});
          })
          .catch((error) => {
            console.error(error);
          });
      },

      _initBeneficiaries() {
        beneficiaryService.getAllBeneficiariesByIdCompany(store.selectedCompany()?.id)
          .then((data: any) => {
            patchState(store, {beneficiaries: data.body.length > 0 ? data.body : undefined});
          });
      },

      _initInvoices() {
        invoiceService.getAllInvoicesByIdCompany(store.selectedCompany()?.id)
          .then((data: any) => {
            patchState(store, {invoices: data.body ?? []});
          });
      },

      _initProductStatisticsData(id : string) {
        statisticService.getProductsStatistics(id)
          .then((response) => {
            patchState(store, {productStatistics: response.body.length > 0 ? response.body : undefined});
          })
          .catch((error) => {
            console.error(error);
          });
      },




    }))
  )
}
