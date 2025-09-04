import { Routes } from '@angular/router';
import {LoginPage} from './authentication/pages/login-page/login-page.component';
import {LandingPageComponent} from './landing/pages/landing-page/landing-page.component';
import {RegisterPage} from './authentication/pages/register-page/register-page.component';
import {AccountValidationPage} from './authentication/pages/account-validation-page/account-validation-page.component';
import {DashboardPage} from './dashboard/pages/dashboard-page/dashboard-page.component';
import {
  TemporaryValidationPage
} from './authentication/pages/temporary-validation-page/temporary-validation-page.component';
import {AuthGuard} from './authentication/services/guards/auth.guard';
import {AuthenticatedPage} from './authentication/pages/authenticated-page/authenticated-page.component';
import {CatalogsPageComponent} from './invoices/pages/catalogs-page/catalogs-page.component';
import {ExpensesPage} from './dashboard/pages/expenses-page/expenses-page.component';
import {RevenuesPage} from './dashboard/pages/revenues-page/revenues-page.component';
import {RequestPaymentPage} from './payments/pages/request-payment-page/request-payment-page.component';
import {TransactionsPage} from './payments/pages/transactions-page/transactions-page.component';
import {BeneficiariesPage} from './payments/pages/beneficiaries-page/beneficiaries-page.component';
import {SuppliersPage} from './payments/pages/suppliers-page/suppliers-page.component';
import {SelectedCompanyGuard} from './settings/companies/services/guards/selected-company-guard.service';
import {GenerateInvoicePage} from './invoices/pages/generate-invoice-page/generate-invoice-page.component';
import {ReceivedInvoicesPage} from './invoices/pages/received-invoices-page/received-invoices-page.component';
import {SendInvoicesPage} from './invoices/pages/send-invoices-page/send-invoices-page.component';
import {TermsConditionsPage} from './authentication/pages/terms-conditions-page/terms-conditions-page.component';
import {HelpPage} from './shared/pages/help-page/help-page.component';
import {ForgotPasswordPageComponent} from './authentication/pages/forgot-password-page/forgot-password-page.component';
import {AddCompanyComponent} from './settings/companies/pages/add-company/add-company.component';
import {SettingsPage} from './settings/page/settings-page/settings-page.component';
import {CompaniesPageComponent} from './settings/companies/pages/companies-page/companies-page.component';
import {
  DisplayCompaniesComponent
} from './settings/companies/pages/companies-page/display-companies/display-companies.component';
import {
  CatalogsDashboardComponent
} from './invoices/pages/catalogs-page/components/catalogs-dashboard/catalogs-dashboard.component';
import {
  AddNewCatalogComponent
} from './invoices/pages/catalogs-page/components/catalogs-dashboard/add-new-catalog/add-new-catalog.component';
import {
  ReceivedPaymentRequestPageComponent
} from './payments/pages/received-payment-request-page/received-payment-request-page.component';
import {UserPageComponent} from './settings/user/pages/user-page/user-page.component';
import {DisplayUserComponent} from './settings/user/pages/user-page/display-user/display-user.component';
import {UpdateUserProfileComponent} from './settings/user/pages/update-user-profile/update-user-profile.component';
import {ListInvoiceGenerateComponent} from './invoices/pages/list-invoice-generate/list-invoice-generate.component';

export const routes: Routes = [
  {
    path: '',
    component: AuthenticatedPage,
    canActivate: [AuthGuard],
    children: [
      {path: 'cancel', redirectTo: 'request-payments-received' },
      {path: 'success', redirectTo: 'request-payments-received' },
      { path: '', component: DashboardPage },
      { path: 'expenses', component: ExpensesPage},
      { path: 'revenues', component: RevenuesPage, canActivate: [SelectedCompanyGuard]},
      { path: 'request-payments', component: RequestPaymentPage, canActivate: [SelectedCompanyGuard]},
      { path: 'transactions', component: TransactionsPage },
      { path: 'beneficiaries', component: BeneficiariesPage, canActivate: [SelectedCompanyGuard] },
      { path: 'suppliers', component: SuppliersPage },
      { path: 'request-payments-received', component: ReceivedPaymentRequestPageComponent },
      { path: 'generate-invoice', component: GenerateInvoicePage, canActivate: [SelectedCompanyGuard] },
      { path: 'list-invoices', component: ListInvoiceGenerateComponent, canActivate: [SelectedCompanyGuard] },
      { path: 'catalogs', component: CatalogsDashboardComponent, canActivate: [SelectedCompanyGuard],
        children: [
          { path: 'display', component: CatalogsPageComponent},
          { path: 'add', component: AddNewCatalogComponent}
        ]},
      { path: 'send-invoices', component: SendInvoicesPage, canActivate: [SelectedCompanyGuard]},
      { path: 'received-invoices', component: ReceivedInvoicesPage },
      { path: 'settings', component: SettingsPage,
        children: [
          { path: 'companies', component: CompaniesPageComponent,
           children: [
             { path: 'display', component: DisplayCompaniesComponent },
             { path: 'add', component: AddCompanyComponent },
             { path: 'edit', component: AddCompanyComponent },
           ]},
          { path: 'user', component: UserPageComponent,
            children: [
              { path: '', redirectTo: 'display', pathMatch: 'full' },
              { path: 'display', component: DisplayUserComponent },
              { path: 'edit/:id', component: UpdateUserProfileComponent },
            ]},
        ]
      },

    ]
  },
  { path: 'landing-page', component: LandingPageComponent },
  { path: 'login', component: LoginPage },
  { path: 'register', component: RegisterPage },
  { path: 'terms-conditions', component: TermsConditionsPage },
  { path: 'confirm-account/:token', component: TemporaryValidationPage },
  { path: 'validation-account', component: AccountValidationPage },
  { path: 'forgot-password', component: ForgotPasswordPageComponent },
  { path: 'help', component: HelpPage }
];
