import {Component, effect, inject, OnInit} from '@angular/core';
import {ChartComponent, ChartType} from "ng-apexcharts";
import {AppStore} from '../../../store/app.store';

@Component({
  selector: 'app-products-charts',
	imports: [
		ChartComponent
	],
  templateUrl: './products-charts.component.html',
  styleUrl: './products-charts.component.css'
})
export class ProductsChartsComponent implements OnInit{

  store = inject(AppStore)


  chartOptions = {

    series: [1, 2, 3],
    chart: {
      width: 380,
      height: 280,
      type: 'polarArea' as ChartType,
    },
    colors: [ '020617', '#ff8f00', '#00897b', '#1e88e5', '#d81b60'],
    labels: ['No Data', 'No Data', 'No Data'],
    responsive: [{
      breakpoint: 480,
      options: {
        chart: {
          width: 200
        },
        legend: {
          position: 'bottom'
        }
      }
    }],
    title: {
      text: 'Product Statistics',
    },
  };

  constructor() {
    effect(() => {
      if (this.store.productStatistics()?.length) {
        this.chartOptions.labels =
          this.store.productStatistics()?.map(product => product.productName) ??  [];
        this.chartOptions.series =
          this.store.productStatistics()?.map(product => parseFloat(product.quantity)) ?? [];
      } else {
        this.chartOptions.labels = ['No Data', 'No Data', 'No Data'];
        this.chartOptions.series = [1, 2, 3];
      }
    });
    // You can initialize the chart options here or in the template

  }

  ngOnInit(): void {
    this.store.initProductStatisticsData();
    }


}
