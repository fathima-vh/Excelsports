import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Goal } from '../../model/Goal';
import { AthleteGoalService } from '../athlete-goal.service';
import { AthleteHeaderComponent } from '../athlete-header/athlete-header.component';
import { AthleteService } from '../athlete.service';

@Component({
  selector: 'app-athlete-view-goals',
  imports: [CommonModule, AthleteHeaderComponent],
  templateUrl: './athlete-view-goals.component.html',
  styleUrls: ['./athlete-view-goals.component.css'] // fixed typo (styleUrl -> styleUrls)
})
export class AthleteViewGoalsComponent implements OnInit {
  athleteGoals: Goal[] = []; // Holds goals data
  athleteId: number = 0;

  constructor(
    private athleteGoalService: AthleteGoalService, // Added Goal Service
    private route: ActivatedRoute,
    private athleteService: AthleteService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.athleteId = Number(sessionStorage.getItem('athleteId'));
    if (this.athleteId) {
      this.fetchAthleteGoals(this.athleteId);
    }
  }

  fetchAthleteGoals(athleteId: number): void {
    this.athleteGoalService.viewAthleteGoals(athleteId).subscribe(
      (goals: Goal[]) => {
        this.athleteGoals = goals;
        console.log('Athlete Goals:', goals);
      },
      (error) => {
        console.error('Error fetching athlete goals:', error);
      }
    );
  }
}
