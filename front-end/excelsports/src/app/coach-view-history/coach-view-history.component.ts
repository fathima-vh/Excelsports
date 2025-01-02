import { Component, OnInit, AfterViewChecked, ChangeDetectorRef, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Router, RouterOutlet } from '@angular/router';
import { Chart, ChartData } from 'chart.js';
import { TrainingHistoryResponseDto } from '../../model/TrainingHistoryResponseDto';
import { TrainingSessionService } from '../training-session.service';
import { AthleteService } from '../athlete.service';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CoachHeaderComponent } from '../coach-header/coach-header.component';

@Component({
  selector: 'app-coach-view-history',
  imports:[CommonModule,FormsModule,CoachHeaderComponent,ReactiveFormsModule],
  templateUrl: './coach-view-history.component.html',
  styleUrls: ['./coach-view-history.component.css'],
})
export class CoachViewHistoryComponent implements OnInit, AfterViewChecked {
  year: number | undefined;
  month: number | undefined;
  day: number | undefined;
  trainingHistory: TrainingHistoryResponseDto[] = [];
  errorMessage: string | null = null;
  athleteId: number = 0;

  caloriesChartData: ChartData<'line'> = {
    labels: [],
    datasets: [
      {
        label: 'Calories Burned',
        data: [],
        borderColor: 'rgba(255, 99, 132, 1)',
        backgroundColor: 'rgba(255, 99, 132, 0.2)',
        fill: true,
      },
    ],
  };

  caloriesChart: Chart | null = null;
  shouldRenderChart = false; // Flag to indicate chart rendering

  constructor(
    private trainingSessionService: TrainingSessionService,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.athleteId = +this.route.snapshot.paramMap.get('athleteId')!;
  }

  ngAfterViewChecked(): void {
    // Render the chart only if the flag is set and the chart doesn't exist
    if (this.shouldRenderChart && !this.caloriesChart) {
      this.renderCaloriesChart();
      this.shouldRenderChart = false; // Reset the flag
    }
  }

  getTrainingHistory(): void {
    if (!this.year) {
      this.errorMessage = 'Year is required.';
      return;
    }

    if (this.month == null) {
      this.month = undefined; // Handle undefined month gracefully
    }

    if (!this.day) {
      this.day = undefined; // Handle undefined day gracefully
    }

    this.errorMessage = null;

    this.trainingSessionService
    .getTrainingHistory(this.athleteId, this.year, this.month, this.day)
    .subscribe({
      next: (data) => {
        // Sort the training history by date (oldest to earliest)
        this.trainingHistory = data.sort(
          (a, b) => new Date(a.date).getTime() - new Date(b.date).getTime()
        );

        // Populate chart data
        this.caloriesChartData.labels = this.trainingHistory.map(
          (session) => session.date
        );
        this.caloriesChartData.datasets[0].data = this.trainingHistory.map(
          (session) => session.totalCaloriesBurned
        );

        // Set the flag to render the chart
        this.shouldRenderChart = true;

        // Manually trigger change detection to ensure DOM updates
        this.cdr.detectChanges();
      },
      error: () => {
        this.errorMessage =
          'An error occurred while fetching training history.';
      },
    });
  }

  renderCaloriesChart(): void {
    const canvas = document.getElementById(
      'caloriesChart'
    ) as HTMLCanvasElement;

    if (!canvas) {
      console.error('Canvas element not found.');
      return;
    }

    this.caloriesChart = new Chart(canvas, {
      type: 'line',
      data: this.caloriesChartData,
      options: {
        responsive: true,
        plugins: {
          legend: { position: 'top' },
          title: {
            display: true,
            text: 'Calories Burned Over Time',
          },
        },
        scales: {
          x: {
            title: { display: true, text: 'Date' },
          },
          y: {
            title: { display: true, text: 'Calories Burned' },
            beginAtZero: true,
          },
        },
      }
    });
  }
  resetFilters(): void {
    this.year = undefined;
    this.month = undefined;
    this.day = undefined;
    this.errorMessage = '';
    this.trainingHistory = [];
    // Optionally, you can also refresh the chart or perform any other actions needed
    console.log('Filters reset');
  }
  
}