import { CommonModule } from '@angular/common';
import { Input, OnInit } from '@angular/core';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Chart, ChartData, ChartOptions } from 'chart.js';
import { NgChartsModule } from 'ng2-charts';
import { Athlete } from '../../model/Athlete';
import { Goal } from '../../model/Goal';
import { TrainingHistoryResponseDto } from '../../model/TrainingHistoryResponseDto';
import { CoachHeaderComponent } from '../coach-header/coach-header.component';
import { CoachService } from '../coach.service';
import { GoalService } from '../goal.service';
import { TrainingHistoryChartComponent } from '../training-history-chart/training-history-chart.component';
import { TrainingSessionService } from '../training-session.service';

@Component({
  selector: 'app-coach-view-athletes',
  imports: [CommonModule,CoachHeaderComponent,FormsModule,NgChartsModule,TrainingHistoryChartComponent],
  templateUrl: './coach-view-athletes.component.html',
  styleUrl: './coach-view-athletes.component.css'
})
export class CoachViewAthletesComponent implements OnInit{

  // @Input() athlete:Athlete|null=null;
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

  coachId: number = Number(sessionStorage.getItem('coachId'));;
  athleteId: number = 0;
  athletes: Athlete[] = [];
  errorMessage: string | null = null;
  loading: boolean = true;

  constructor(
    private coachService: CoachService,
    private goalService: GoalService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    // this.coachId = this.coachService.getCoachId();
    this.athleteId = this.goalService.getAthleteId();
    this.coachId=Number(sessionStorage.getItem('coachId'));
    console.log("check:"+this.coachId);
    this.fetchAthleteDetails();
  }

  fetchAthleteDetails(): void {
    this.loading = true;
    this.coachService.getAthletesByCoachId(this.coachId).subscribe({
      next: (athletelist: Athlete[]) => {
        this.athletes = athletelist;
        this.loading = false;
      },
      error: (err) => {
        this.errorMessage = 'Unable to fetch athletes. Please try again later.';
        this.loading = false;
      },
    });
  }

 
  // viewHistory(athleteId:number):void{
  //   this.router.navigate(['/coach-view-history']);
  // }
  messageToAthlete(athleteId:number):void{
    console.log("message athlete");
    
  }
  addGoals(athleteId:number):void{
    if (!this.coachId) {
      console.error("Coach data is not available");
      return;
    }
    console.log(`Adding goals for athlete ID: ${athleteId} by coach ID: ${this.coachId}`);
    this.router.navigate(['/coach-set-goals', athleteId]);
  }
  getTrainingHistory(athleteId:number){
    this.router.navigate(['/coach-view-history', athleteId]);
  }
  
}
