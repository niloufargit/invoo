export type Invoice = {
  id: number,
  invoiceNumber: string,
  customerName: string,
  customerEmail: string,
  totalAmount: number;
  currency: number,
}


export interface InvoiceGateway {
  getAllInvoicesByIdCompany(idCompany: string | undefined): Promise<any>;
}
