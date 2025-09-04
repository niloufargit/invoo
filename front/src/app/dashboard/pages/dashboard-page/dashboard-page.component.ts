import {Component} from '@angular/core';
import {LineChartComponent} from "../../../shared/components/charts/line-chart/line-chart.component";
import {PageTemplateComponent} from '../../../shared/components/page-template/page-template.component';
import {
  DisplayRequestPaymentPageComponent
} from '../../../payments/pages/display-request-payment-page/display-request-payment-page.component';
import {PieChartsComponent} from '../../../shared/components/charts/pie-charts/pie-charts.component';
import {ProductsChartsComponent} from '../../../shared/components/charts/products-charts/products-charts.component';

@Component({
  selector: 'app-dashboard-page',
  imports: [
    LineChartComponent,
    PageTemplateComponent,
    DisplayRequestPaymentPageComponent,
    PieChartsComponent,
    ProductsChartsComponent
  ],
  templateUrl: './dashboard-page.component.html',
  styleUrl: './dashboard-page.component.css'
})
export class DashboardPage {

}
