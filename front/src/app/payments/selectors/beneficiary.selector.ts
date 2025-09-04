import {patchState, signalStoreFeature, type, withComputed, withMethods} from '@ngrx/signals';
import {AppState} from '../../shared/store/models/store.models';
import {computed, inject} from '@angular/core';
import {BeneficiaryService} from '../services/beneficiary.service';
import {Company} from '../../settings/companies/models/company.models';

export function withBeneficiarySelectors() {
  return signalStoreFeature(
    {state: type<AppState>()},


    withComputed((store) => ({
      filteredBeneficiaries: computed(() => {
        if (store.beneficiaryFilter() === undefined || store.beneficiaryFilter() === '') {
          return store.beneficiaries();
        }
        return store.beneficiaries().filter((beneficiary: Company) => {
          return beneficiary?.name.toLowerCase().includes(store.beneficiaryFilter()?.toLowerCase() ?? "")
        })
      }),

    })),


    withMethods((store,
                 beneficiaryService = inject(BeneficiaryService),
    ) => ({

      setIsBeneficiaryAdded: (value: boolean) => {
        patchState(store, {isBeneficiaryAdded: value});
      },

      async getAllBeneficiariesByIdCompany() {
        beneficiaryService.getAllBeneficiariesByIdCompany(store.selectedCompany()?.id)
          .then((data: any) => {
            patchState(store, {beneficiaries: data.body.length > 0 ? data.body : undefined});
          });
      },

      _beneficiariesByIdCompany() {
        beneficiaryService.getAllBeneficiariesByIdCompany(store.selectedCompany()?.id)
          .then((data: any) => {
            patchState(store, {beneficiaries: data.body});
          });
      },

      async searchIndividualBeneficiary(email: string): Promise<any> {
        patchState(store, {isLoaded: true});
        return beneficiaryService.searchIndividualBeneficiary(email)
          .then((data: any) => {
            patchState(store, {isLoaded: false});
            return data.body;
          });
      },

      async searchProfessionalBeneficiary(name: string, sirenNumber?: string): Promise<any> {
        patchState(store, {isLoaded: true});
        return beneficiaryService.searchProfessionalBeneficiary(name, sirenNumber!)
          .then((data: any) => {
            patchState(store, {isLoaded: false});
            return data.body;
          });
      },
      async addBeneficiary(email:string|null,idCompany:string|null,idSelectedCompany:string|undefined): Promise<any> {
        patchState(store, {isBeneficiaryAdded: true});
        return beneficiaryService.addBeneficiary(email, idCompany, idSelectedCompany)
          .then((data: any) => {
            patchState(store, {isBeneficiaryAdded: false});
            this._beneficiariesByIdCompany();
            return data.body;
          });
      },

      ifBeneficiaryExists(id: string | undefined): boolean {
        const beneficiaries = store.beneficiaries();
        if (beneficiaries === undefined || beneficiaries.length === 0) {
          return false;
        } else {
          return !!beneficiaries.find((beneficiary: Company) => beneficiary?.id === id?.toString());
        }
      },
      beneficiaryFilterKey(key: string | undefined) {
        if (key === undefined || key === null || key === '') {
          patchState(store, {beneficiaryFilter: undefined});
          return;
        }
        patchState(store, {beneficiaryFilter: key});
      },
      deleteBeneficiary(idBeneficiary: string) {
        beneficiaryService.deleteBeneficiary(idBeneficiary, store.selectedCompany()?.id)
          .then(() => {
            patchState(store, {beneficiaries: store.beneficiaries().filter((beneficiary: Company) => beneficiary?.id !== idBeneficiary)});
            if (store.beneficiaries().length === 0) {
              patchState(store, {beneficiaries: undefined});
            }
          });
      },

    }))
  );
}


export type BeneficiarySearchResult = {idCompany: string, name:string, sirenNumber:string};
