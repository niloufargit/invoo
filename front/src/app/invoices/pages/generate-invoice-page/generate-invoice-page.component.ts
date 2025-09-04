import {Component, inject} from '@angular/core';
import {PageTemplateComponent} from "../../../shared/components/page-template/page-template.component";
import {CardComponentComponent} from '../../../shared/components/cards/card-component/card-component.component';
import {AppStore} from '../../../shared/store/app.store';
import {
  GenerateInvoicePageHeaderComponent
} from './components/generate-invoice-page-header/generate-invoice-page-header.component';
import {
  GenerateInvoicePageContentComponent
} from './components/generate-invoice-page-content/generate-invoice-page-content.component';

@Component({
  selector: 'app-generate-invoice-page',
  imports: [
    PageTemplateComponent,
    CardComponentComponent,
    GenerateInvoicePageHeaderComponent,
    GenerateInvoicePageContentComponent,
  ],
  templateUrl: './generate-invoice-page.component.html',
  styleUrl: './generate-invoice-page.component.css'
})
export class GenerateInvoicePage {

  protected store = inject(AppStore);

}
