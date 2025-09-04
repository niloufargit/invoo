import {patchState, signalStoreFeature, type, withMethods} from '@ngrx/signals';
import {AppState} from '../models/store.models';
import {inject} from '@angular/core';
import {ProductService} from '../../../invoices/services/product.service';
import {CatalogService} from '../../../invoices/services/catalog.service';
import {BeneficiaryService} from '../../../payments/services/beneficiary.service';
import {InvoiceService} from '../../../invoices/services/invoice.service';
import {StatisticsService} from '../../../invoices/services/statistics.service';


export function withStatisticsDataSelectors() {
  return signalStoreFeature(
    {
      state: type<AppState>(),
    },

    withMethods((
      {selectedCatalog, products,...store},
      statisticService = inject(StatisticsService),
    ) => ({


      initProductStatisticsData() {
        const id = selectedCatalog()?.id.toString() || '0';
        if (id === '0') return;
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


export type ProductStatistic = {
  id: string;
  companyId: string;
  productName: string;
  quantity: string;
  totalPriceHT: string;
  totalPriceTTC: string;
  pcQuantity: string;
  pcTotalPriceHT: string;
  pcTotalPriceTTC: string;
}

