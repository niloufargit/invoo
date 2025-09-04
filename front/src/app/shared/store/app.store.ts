import {getState, patchState, signalStore, withHooks, withMethods, withState} from '@ngrx/signals';
import {AppState} from './models/store.models';
import {initialState} from './app.state';
import {effect} from '@angular/core';
import {withCompaniesSelector} from '../../settings/companies/selectors/company.selector';
import {withUserSelector} from '../../settings/user/selectors/user.selector';
import {withCatalogSelectors} from '../../invoices/selectors/catalog.selector';
import {withProductSelectors} from '../../invoices/selectors/product.selector';
import {withBeneficiarySelectors} from '../../payments/selectors/beneficiary.selector';
import {withInvoiceSelectors} from '../../invoices/selectors/invoice.selector';
import {withPaymentRequestSelectors} from '../../payments/selectors/payment.selector';
import {withInitDataSelectors} from './init-data/init.data.selector';
import {withStatisticsDataSelectors} from './init-data/statistic.selector';

export const AppStore = signalStore(
  {providedIn: "root"},
  withState<AppState>(initialState),

  withInitDataSelectors(),
  withUserSelector(),
  withCompaniesSelector(),
  withCatalogSelectors(),
  withProductSelectors(),
  withBeneficiarySelectors(),
  withInvoiceSelectors(),
  withPaymentRequestSelectors(),
  withStatisticsDataSelectors(),

  withMethods((store) => (({

    openedModal() {
      this._openModal();
    },
    closedModal() {
      this._closeModal();
    },
    _openModal() {
      patchState(store, {closeModal: false});
    },
    _closeModal() {
      patchState(store, {closeModal: true});
    },
    getAllInfo(){

    },
  }))),

  withHooks({
    onInit(
      store,
    ) {
      const storeFromStorage = JSON.parse(
        localStorage.getItem('store') || '{}'
      );
      patchState(store, storeFromStorage);
      effect(() => {
        const state = getState(store);
        localStorage.setItem('store', JSON.stringify(state));
      });
    }
  }),


)
