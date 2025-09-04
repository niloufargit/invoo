import {patchState, signalStoreFeature, type, withComputed, withMethods} from '@ngrx/signals';
import {AppState} from '../../shared/store/models/store.models';
import {computed, inject} from '@angular/core';
import {CatalogService} from '../services/catalog.service';
import {Catalog, CatalogWithCategories, CategoryDto} from '../models/catalog.model';

export function withCatalogSelectors() {
  return signalStoreFeature(
    {
      state: type<AppState>(),
    },

    withComputed(({selectedCatalog, catalogs}) => ({
      idSelectedCatalog: computed(() => {
        return selectedCatalog()?.id || 0;
      }),

    })),


    withMethods((
      {catalogs,...store},
      catalogService = inject(CatalogService),
    ) => ({

      addCatalog(catalog: Catalog){
        if (catalog){
          patchState(store, {isLoaded: true})
          catalogService.createCatalog(catalog)
            .then((response) => {
              patchState(store, {catalogs: catalogs()?.concat(response.body as CatalogWithCategories)})
              patchState(store, {createdCatalog: response.body  as CategoryDto})
              patchState(store, {isLoaded: false})
            })
            .catch((error) => {
              console.error('Error creating catalog:', error);
              patchState(store, {isLoaded: false});
            });
        }
      },

      updateCatalog(catalog: Catalog){
        patchState(store, {isLoaded: true})
        if (catalog) {
          catalogService.updateCatalog(catalog.id, catalog)
            .then((response) => {
              const updatedCatalog = response.body as CatalogWithCategories;
              const updatedCatalogs = catalogs()?.map(c => c.id === updatedCatalog.id ? updatedCatalog : c);
              patchState(store, {catalogs: updatedCatalogs});
              patchState(store, {createdCatalog: response.body});
              patchState(store, {isLoaded: false});
            })
            .catch((error) => {
              console.error('Error updating catalog:', error);
              patchState(store, {isLoaded: false});
            })
        }
      },
      getCategoriesCreatedCatalog() {
        return catalogs()?.find((c) => c.id === store.createdCatalog()?.id)?.categories || [];
      },

      selectCatalog(catalog: CatalogWithCategories) {
        patchState(store, {selectedCatalog: catalog});
      },

      clearCreatedCatalog() {
        patchState(store, {createdCatalog: undefined});
        patchState(store, {createdProducts: undefined});
      },

      selectCategory(id: number) {
        if (Number.isNaN(id)) {
          patchState(store, {selectedCategoryId: undefined});
          return;
        }
        patchState(store, {selectedCategoryId: id});
      },

      unselectCategory() {
        patchState(store, {selectedCategoryId: undefined});
      },

      getNameCategory(idCatalog: number, idCategory: number) {
        const category = catalogs()?.find((c) => c.id === idCatalog);
        const cat = category?.categories.find((c) => c.id === idCategory);
        return cat ? cat.name : '';
      },

      deleteCatalog(id: number | undefined) {
        if (id === undefined) return;
        patchState(store, {isLoaded: true});
        catalogService.deleteCatalog(id)
          .then(() => {
            patchState(store, {createdCatalog: undefined});
            patchState(store, {isLoaded: false});
            patchState(store, {createdProducts: undefined});
          })
          .catch((error) => {
            patchState(store, {isLoaded: false});
            console.error('Error deleting catalog:', error);
          });

      },


      async getAllCatalogs() {
        const id = store.selectedCompany()?.id;
        if (id)
          catalogService.getCatalogsByCompanyId(id)
          .then((response) => {
            patchState(store, {catalogs: response.body});
            if (response.body.length === 0) {
              patchState(store, {selectedCatalog: undefined});
            }
          })
          .catch((error) => {
            console.error(error);
          });
      }



    }))
  )
}
