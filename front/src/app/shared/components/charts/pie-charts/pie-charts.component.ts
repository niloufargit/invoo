import { Component } from '@angular/core';
import {ChartComponent} from 'ng-apexcharts';
import {ChartType} from 'ng-apexcharts';

@Component({
  selector: 'app-pie-charts',
  imports: [
    ChartComponent
  ],
  templateUrl: './pie-charts.component.html',
  styleUrl: './pie-charts.component.css'
})
export class PieChartsComponent {

  chartOptions = {
    series: [44, 55, 13, 43, 22],
    chart: {
      width: 380,
      height: 280,
      type: 'pie' as ChartType,
    },
    colors: [ '020617', '#ff8f00', '#00897b', '#1e88e5', '#d81b60'],
    labels: ['Team A', 'Team B', 'Team C', 'Team D', 'Team E'],
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
      text: 'Test'
    },
  };

}
