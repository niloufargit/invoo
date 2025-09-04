import {patchState, signalStoreFeature, type, withMethods} from '@ngrx/signals';
import {AppState} from '../../shared/store/models/store.models';
import {inject} from '@angular/core';
import {ProductService} from '../services/product.service';
import {ProductApi, ProductSaving} from '../models/product.model';


export function withProductSelectors() {
  return signalStoreFeature(
    {
      state: type<AppState>(),
    },
    withMethods((
      {selectedCatalog, products,...store},
      productService = inject(ProductService),
    ) => ({

      getProductsByCatalogId(id: string) {
        productService.getProductsByCatalogId(id)
          .then((response) => {
            patchState(store, {products: response.body});
          })
          .catch((error) => {
            console.error(error);
          });
      },

      filteredProducts(key: string, selectedCategoryId: number) {
        if (key === '' && !selectedCategoryId) {
          return products();
        }
        if (selectedCategoryId) {
          return products()?.filter((product) => {
            return product.idCategory === selectedCategoryId && product.name.toLowerCase().includes(key.toLowerCase());
          });
        }
        return  products()?.filter((product) => {
          return product.name.toLowerCase().includes(key.toLowerCase());
        });
      },

      editProduct(editedProduct: ProductApi) {

        patchState(store, {isLoaded: true})
        productService.updateProduct(editedProduct.id, editedProduct)
          .then((response) => {
            patchState(store, {products: products()?.map((product) => {
              if (product.id === editedProduct.id) {
                return editedProduct
              }
              return product;
            })});
            patchState(store, {isLoaded: false})
            patchState(store, {closeModal: true})
          })
          .catch((error) => {
            patchState(store, {isLoaded: false})
            console.error('Error updating product:', error);
          })

      },

      deleteProduct(productId: string) {
        patchState(store, {isLoaded: true})
        productService.deleteProduct(productId)
          .then(() => {
            patchState(store, {products: products()?.filter((product) => product.id !== productId)});
            patchState(store, {isLoaded: false})

          })
          .catch((error) => {
            patchState(store, {isLoaded: false})
            console.error('Error deleting product:', error);
          });
      },

      async addProduct(newProduct: ProductSaving): Promise<boolean> {
        patchState(store, {isLoaded: true})
        try {
          const response = await productService.createProduct(newProduct);
          patchState(store, {createdProducts: [...(store.createdProducts() || []), ...response.body]});
          patchState(store, {isLoaded: false});
          return true;
        } catch (error) {
          patchState(store, {isLoaded: false});
          console.error('Error creating product:', error);
          return false;
        }
      }

    }))
  )
}
