import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Athlete } from '../../model/Athlete';
import { Goal } from '../../model/Goal';
import { TrainingSession } from '../../model/TrainingSession';
import { ViewTrainingSession } from '../../model/ViewTrainingSession';
import { AthleteGoalService } from '../athlete-goal.service';
import { AthleteHeaderComponent } from '../athlete-header/athlete-header.component';
import { AthleteService } from '../athlete.service';
import { TrainingSessionService } from '../training-session.service';
import { GoalService } from '../goal.service';
import { ExerciseService } from '../exercise.service';

@Component({
  selector: 'app-athlete-home',
  imports: [FormsModule, CommonModule, AthleteHeaderComponent],
  templateUrl: './athlete-home.component.html',
  styleUrls: ['./athlete-home.component.css'],
})
export class AthleteHomeComponent implements OnInit {
  athleteDetails: Athlete | null = null;
  latestTrainingSession: ViewTrainingSession | null = null;
  athleteGoals: Goal[] = []; // Holds goals data
  athleteProgress: any = {}; // Holds the progress data from backend
  athleteId: number = 0;

  quote: string = ''; 
  author: string = '';

  constructor(
    private trainingSessionService: TrainingSessionService,
    private athleteGoalService: AthleteGoalService, 
    private goalService: GoalService, // Added Goal Service to fetch progress
    private route: ActivatedRoute,
    private athleteService: AthleteService,
    private router: Router,
    private exerciseService: ExerciseService
  ) {}

  ngOnInit(): void {
    const email = sessionStorage.getItem('email');
    if (email) {
      this.fetchAthleteDetails(email);
    } else {
      alert('No athlete is logged in!');
      this.router.navigate(['/login']);
    }

    this.athleteId = Number(sessionStorage.getItem('athleteId'));
    if (this.athleteId) {
      this.getLatestTrainingSession(this.athleteId);
      this.fetchAthleteGoals(this.athleteId);
      this.getAthleteProgress(this.athleteId); // Fetch progress when page loads
    }

    this.loadRandomQuote();
  }

  loadRandomQuote() {
    this.athleteService.getQuote().subscribe(
      (response: any) => {
        this.quote = response.quote;  // Assuming the response has a `quote` field
        this.author = response.author;  // Assuming the response has an `author` field
      },
      (error) => {
        console.error('Error fetching quote:', error);
      }
    );
  }

  fetchAthleteDetails(email: string): void {
    this.athleteService.getAthleteDetails(email).subscribe(
      (data: Athlete) => {
        this.athleteDetails = data;
        this.athleteService.setAthleteId(data.id);
        sessionStorage.setItem('goalCalorie',data.goalCalorie.toString());
        sessionStorage.setItem('athleteId', data.id.toString());
      },
      (error) => {
        console.error('Error fetching athlete details:', error);
        alert('Failed to load athlete details. Please try again later.');
      }
    );
  }

  getLatestTrainingSession(athleteId: number): void {
    this.trainingSessionService.getLatestSession(athleteId).subscribe(
      (session: ViewTrainingSession) => {
        this.latestTrainingSession = session;
      },
      (error) => {
        console.error('Error fetching latest session:', error);
      }
    );
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

  getAthleteProgress(athleteId: number): void {
    this.goalService.getAthleteProgress(athleteId).subscribe(
      (progress: any) => {
        this.athleteProgress = progress;
        console.log('Athlete Progress:', this.athleteProgress);
      },
      (error) => {
        console.error('Error fetching athlete progress:', error);
      }
    );
  }

  /**
   * This method exposes `Object.keys()` to the Angular template.
   */
  objectKeys(obj: any): string[] {
    if (!obj) {
      return [];
    }
    return Object.keys(obj);
  }

  exerciseDetails: any;
  exerciseName: string = ''; 
  showModal: boolean = false;
  errorMessage:string = '';
  fetchExerciseDetails(exerciseName: string): void {
    this.exerciseService.getExerciseDetails(exerciseName.toLowerCase()).subscribe(
      (data) => {
        this.exerciseDetails = data;
        this.errorMessage = '';
        console.log("api response : ",this.exerciseDetails);
        
        
        this.showModal = true;  
      },
      (error) => {
        if (error.status === 403) {
          this.errorMessage = 'You are not subscribed to this API. Please check your subscription.';
        } else {
          this.errorMessage = 'An error occurred while fetching exercise details. Please try again later.';
        }
        console.error('Error fetching exercise details', error);
        this.showModal = false; // Hide the modal on error
      
      }
    );
  }
  closeModal(): void {
    this.showModal = false;
  }
  
}
