import {HttpResponse} from '@angular/common/http';

export type PaymentRequest = {
  companyId: string,
  invoiceId: string,
  invoiceName: string,
  amountInvoice: number,
  recipientId: string,
  recipientType: RecipientType,
  recipientName: string,
  recipientEmail: string,
  senderId: string,
  recipientExternalEmail: string
}

export type PaymentResponse = {
  paymentRequestId: string,
  recipientId: string,
  recipientName: string,
  recipientEmail: string,
  invoiceId: string,
  invoiceName: string,
  amount: number,
  status: Status,
  createdAt: string,
}

export type PaymentRequestResponse = {
  companyId: string,
  senderId: string,
  recipientId: string,
  status: Status,
  invoiceId: string,
  invoiceName: string,
  amount: number,
  createdAt: string,
  sessionUrl: string
}

export type RecipientType = "USER" | "COMPANY";
export type Status = "CREATED" | "FAILED" | "PAID" | "UNPAID";

export interface PaymentRequestGateway {
  provideCheckoutUrlSession(request: PaymentRequest): Promise<HttpResponse<PaymentResponse>>;
}
