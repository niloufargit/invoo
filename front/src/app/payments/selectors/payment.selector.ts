import {patchState, signalStoreFeature, type, withMethods} from '@ngrx/signals';
import {AppState} from '../../shared/store/models/store.models';
import {inject} from '@angular/core';
import {PaymentRequestService} from '../services/paymentRequest.service';
import {PaymentRequest} from '../models/paymentRequest.model';
import {Router} from '@angular/router';

export function withPaymentRequestSelectors() {
  return signalStoreFeature(
    {
      state: type<AppState>(),
    },
    withMethods((
      {paymentRequests,...store},
      paymentRequestService = inject(PaymentRequestService),
      router = inject(Router)
    ) => ({

      getAllPaymentRequestsByCompanyId() {
        paymentRequestService.getAllPaymentRequestsByCompanyId(store.selectedCompany()?.id)
          .then((data: any) => {
            patchState(store, {paymentRequests: data.body});
          })
          .catch((error) => {
            console.error(error);
          });
      },

       createPaymentRequest(request: PaymentRequest) {
          paymentRequestService.provideCheckoutUrlSession(request)
          .then(async (response: any) => {
            patchState(store, {paymentRequests: paymentRequests()?.concat(response ? response.body : [])});
            patchState(store, {invoiceBeneficiary: undefined, selectedInvoice: undefined});
            router.navigate(['request-payments']);
          })
            .catch ((error)=> {
          console.error(error);
        });
      },

      getAllPaymentRequestsReceivedByCompanyId(){
        paymentRequestService.getAllPaymentRequestsReceivedByCompanyId(store.selectedCompany()?.id)
          .then((data: any) => {
            patchState(store, {paymentRequestsReceived: data.body});
          })
          .catch((error) => {
            console.error(error);
          });
      }
    }))
  )
}
