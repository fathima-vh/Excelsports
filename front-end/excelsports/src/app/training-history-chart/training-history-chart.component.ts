import { Component, Input } from '@angular/core';
import { ChartData, ChartOptions } from 'chart.js';
import { NgChartsModule } from 'ng2-charts';

@Component({
  selector: 'app-training-history-chart',
  templateUrl: './training-history-chart.component.html',
  styleUrls: ['./training-history-chart.component.css']
})
export class TrainingHistoryChartComponent {
  @Input() chartData: ChartData<'line'> | undefined;
  @Input() chartOptions: ChartOptions = {
    responsive: true,
    scales: {
      x: {
        title: {
          display: true,
          text: 'Date',
        },
      },
      y: {
        title: {
          display: true,
          text: 'Training Sessions',
        },
      },
    },
  };
}
