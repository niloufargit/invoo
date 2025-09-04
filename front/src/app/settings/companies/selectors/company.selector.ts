import {patchState, signalStoreFeature, type, withComputed, withMethods} from '@ngrx/signals';
import {AppState} from '../../../shared/store/models/store.models';
import {computed, inject} from '@angular/core';
import {Company, GetCompanyByIdResponse} from '../models/company.models';
import {CompaniesService} from '../services/companies.service';
import {Router} from '@angular/router';

export function withCompaniesSelector() {

  return signalStoreFeature(
    {
      state: type<AppState>(),
    },

    withComputed(({selectedCompany, companies}) => ({
      nameCompany: computed(() => {
        return selectedCompany()?.name;
      }),
      hasCompany: computed(() => {
        // @ts-ignore
        return companies().length > 0;
      }),
    })),

    withMethods(({companies,...store},
                 companiesService = inject(CompaniesService),
                 router = inject(Router)
    ) => ({
      selectCompany(company: GetCompanyByIdResponse) {
        if(company && Object.keys(company).length > 0){
          patchState(store, {selectedCompany: company});
          patchState(store, {currentActiveName: company?.name});
          patchState(store, {currentActiveId: company?.id});
        }
        else if(!company){
          const user = store.user();
          patchState(store, {selectedCompany: {} as GetCompanyByIdResponse});
          patchState(store, {products: []});
          patchState(store, {currentActiveName: user?.firstname});
          patchState(store, {currentActiveId: user?.id});
        }
      },

      createCompany(data: Company) {
        patchState(store, {isLoaded: true});
        companiesService.createCompany(data)
          .then((response) => {
            // @ts-ignore
            patchState(store, {companies: companies().concat(response?response.body:[])});
            patchState(store, {isLoaded: false});
            router.navigate(['settings/companies/display']);
          })
          .catch((error) => {
            patchState(store, {errorLoading: true});
            console.error(error);
          });
      },
      getAllCompanies() {
        companiesService.getCompanies()
          .then((response) => {
            patchState(store, {companies: response.body});
          })
          .catch((error) => {
            console.error(error);
          });
      },

      updateCompany(id: string, data: Company) {
        patchState(store, {isLoaded: true});
        companiesService.updateCompany(id, data)
          .then((response) => {
            const updatedCompany = response.body;
            const companiesList = companies()?.map(company => company.id === updatedCompany.id ? updatedCompany : company);
            patchState(store, {companies: companiesList});
            patchState(store, {isLoaded: false});
            if (updatedCompany.id === store.selectedCompany()?.id) {
              patchState(store, {currentActiveName: updatedCompany.name});
            }
            router.navigate(['settings/companies/display']);
          })
          .catch((error) => {
            patchState(store, {errorLoading: true});
            console.error(error);
          });
      },

      isSelectedCompany() {
        const company = store.selectedCompany();
        return company && Object.keys(company).length > 0;
      },

      unselectCompany() {
        const user = store.user();
        patchState(store, {selectedCompany: {} as GetCompanyByIdResponse});
        patchState(store, {currentActiveName: user?.firstname});
        patchState(store, {currentActiveId: user?.id});
      }

    }))
  )
}
