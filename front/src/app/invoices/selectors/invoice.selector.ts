import {patchState, signalStoreFeature, type, withMethods} from '@ngrx/signals';
import {AppState} from '../../shared/store/models/store.models';
import {InvoiceAPI, InvoiceContent} from '../models/invoice.model';
import {inject} from '@angular/core';
import {InvoiceService} from '../services/invoice.service';
import {Beneficiary} from '../../payments/models/beneficiary.model';
import {Product} from '../models/product.model';

export function withInvoiceSelectors() {
  return signalStoreFeature(
    {
      state: type<AppState>(),
    },
    withMethods((
      {invoices, invoiceProducts, ...store},
      invoiceService = inject(InvoiceService),
    ) => ({

      selectInvoiceBeneficiary(beneficiary: Beneficiary) {
        patchState(store, {invoiceBeneficiary: beneficiary});
      },

      removeInvoiceBeneficiary() {
        patchState(store, {invoiceBeneficiary: undefined});
      },

      addInvoiceProduct(product: Product) {
        if(!invoiceProducts()){
          patchState(store, {invoiceProducts: []});
        }
        patchState(store, {invoiceProducts: invoiceProducts()?.concat(product)});
      },

      productIsSelected(product: Product): boolean {
        return invoiceProducts()?.some(p => p?.id === product?.id) || false;
      },

      getProductQuantity(product: Product): number | undefined {
        const selectedProduct = invoiceProducts()?.find(p => p?.id === product?.id);
        return selectedProduct ? selectedProduct.quantity : undefined;
      },

      getTotalHT(): number {
        return parseFloat((invoiceProducts()?.reduce((total, product) => {
          return total + (parseFloat(product?.htPrice ?? '0') * (product?.quantity ?? 0));
        }, 0) ?? 0).toFixed(2));
      },

      getTotalTTC(): number {
        return parseFloat((invoiceProducts()?.reduce((total, product) => {
          return total + (parseFloat(product?.htPrice ?? '0') * (product?.quantity ?? 0) * (1 + (parseFloat(product?.vatRate ?? '0') / 100)));
        }, 0) ?? 0).toFixed(2));
      },

      updateProductSelectedInvoiceQuantity(product: Product, quantity: number) {
        if(!invoiceProducts()) {
          patchState(store, {invoiceProducts: []});
        }
        if(quantity < 0) {
          console.warn("Quantity cannot be negative");
          return;
        }
        if(quantity === 0) {
          this.removeInvoiceProduct(product);
          return;
        }
        if(quantity === 1 && !this.productIsSelected(product)) {
          if(product){
            product.quantity = 1;
          }
          this.addInvoiceProduct(product);
        }
        const currentProducts = invoiceProducts() as Product[] | undefined;
        if(product){
          patchState(store, {
            invoiceProducts: currentProducts?.map((p) => {
              if (p?.id === product.id) {
                console.log(p);
                return { ...p, quantity: quantity };
              }
              return p;
            }),
          });
        }
      },

      removeInvoiceProduct(product: Product) {
        patchState(store, {invoiceProducts: invoiceProducts()?.filter(p => p?.id !== product?.id)});
        if(invoiceProducts()?.length === 0) {
          patchState(store, {invoiceProducts: undefined});
        }
      },

      generateInvoiceNumber() {
        if(invoices()?.length === 0) {
          return `INV-0001`;
        }
        const lastInvoice = invoices()?.[invoices()?.length - 1];
        const lastInvoiceNumber = lastInvoice?.invoiceNumber ?? 'INV-0000';
        const lastNumber = parseInt(lastInvoiceNumber.split('-')[1], 10);
        return `INV-${String(lastNumber + 1).padStart(4, '0')}`;
      },

      generateInvoice() {
      const invoiceContent: InvoiceContent = {
          invoiceNumber: this.generateInvoiceNumber(),
          invoiceTitle: this.generateInvoiceNumber(),
          beneficiary: store.invoiceBeneficiary() as Beneficiary,
          companyId: parseFloat(store.selectedCompany()?.id ?? ''),
          products: invoiceProducts() ?? [],
          deliveryDate: new Date().toISOString(),
          price: {
            unitPrice: 0,
            totalExcludingTax: this.getTotalHT() ?? 0,
            totalIncludingTax: this.getTotalTTC() ?? 0,
          },
        };
        invoiceService.generateInvoice(invoiceContent)
          .then((response) => {
            patchState(store, {invoices: invoices()?.concat(response.body)});
            patchState(store, {invoiceBeneficiary: undefined});
            patchState(store, {invoiceProducts: undefined});

            console.log('Invoice generated successfully:', response.body);
          })
          .catch((error) => {
            console.error(error);
          });
      },
      selectInvoice(id : number) {
        const selectedInvoice = invoices()?.find(invoice => invoice.id === id);
        if (selectedInvoice) {
          patchState(store, {selectedInvoice});
        } else {
          console.warn(`Invoice with id ${id} not found.`);
        }
      },
      getAllInvoicesByIdCompany() {
        invoiceService.getAllInvoicesByIdCompany(store.selectedCompany()?.id)
          .then((data: any) => {
            patchState(store, {invoices: data.body});
          })
          .catch((error) => {
            console.error(error);
          });
      },
      async getInvoiceContentById(id: number | undefined) {
        try {
          const response = await invoiceService.getInvoiceContentById(id);
          console.log(response.body.htmlContent)
          return response.body.htmlContent;
        } catch (error) {
          console.error(error);
          return await Promise.reject(error);
        }
      },
      downloadInvoicePdf(id: number | undefined) {
        invoiceService.downloadInvoicePdf(id)
          .then(() => {
            console.log('Invoice PDF downloaded successfully');
          })
          .catch((error) => {
            console.error('Error downloading invoice PDF:', error);
          });
      },
    }))
  )
}
