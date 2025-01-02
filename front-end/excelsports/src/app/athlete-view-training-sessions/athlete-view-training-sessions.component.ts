import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Athlete } from '../../model/Athlete';
import { ViewTrainingSession } from '../../model/ViewTrainingSession';
import { AthleteHeaderComponent } from '../athlete-header/athlete-header.component';
import { AthleteService } from '../athlete.service';
import { TrainingSessionService } from '../training-session.service';

@Component({
  selector: 'app-athlete-view-training-sessions',
  imports:[FormsModule,CommonModule,AthleteHeaderComponent],
  templateUrl: './athlete-view-training-sessions.component.html',
  styleUrl: './athlete-view-training-sessions.component.css'
})
export class AthleteViewTrainingSessionsComponent implements OnInit {
  athleteDetails: Athlete | null = null;
  trainingSessions: ViewTrainingSession[] = [];
  filteredSessions: ViewTrainingSession[] = [];  // Store filtered sessions
  searchDate: string = '';  // Bound to search input

  athleteId: number;

  constructor(private athleteService: AthleteService, private router: Router,private trainingSessionService: TrainingSessionService) {
    this.athleteId = Number(sessionStorage.getItem('athleteId'));
  }

  ngOnInit(): void {
    const email = sessionStorage.getItem('email'); 
    if (email) {
      this.fetchAthleteDetails(email);
    } else {
      alert('No athlete is logged in!');
      this.router.navigate(['/login']);
    }
    this.fetchTrainingSessions();
  }

  fetchTrainingSessions(): void {
    console.log("view ts check :",this.athleteId);

    this.trainingSessionService.viewTrainingSession(this.athleteId).subscribe({
      next: (sessions) => {
        this.trainingSessions = sessions;
        this.filteredSessions = [...this.trainingSessions];  // Initially show all sessions
        console.log('Training sessions:', this.trainingSessions);
      },
      error: (err) => {
        console.error('Error fetching training sessions:', err);
      },
    });
  }

  filterSessionsByDate(): void {
    if (this.searchDate) {
      // Filter based on the selected date
      this.filteredSessions = this.trainingSessions.filter((session) =>
        session.date === this.searchDate
      );
    } else {
      // If no date is selected, show all sessions
      this.filteredSessions = [...this.trainingSessions];
    }
  }

  fetchAthleteDetails(email: string): void {
    this.athleteService.getAthleteDetails(email).subscribe(
      (data: Athlete) => {
        this.athleteDetails = data;
        this.athleteService.setAthleteId(data.id);
        console.log("home :",data.id);
        sessionStorage.setItem('athleteId',data.id.toString());
        console.log('Athlete details fetched successfully:', data);
      },
      (error) => {
        console.error('Error fetching athlete details:', error);
        alert('Failed to load athlete details. Please try again later.');
      }
    );
  }

  editTrainingSession(session: any) {
    this.router.navigate(['/athlete-edit-training-session', session.id]);
  }

  deleteSession(sessionId: number) {
    if (confirm('Are you sure you want to delete this training session?')) {
      this.trainingSessionService.deleteTrainingSession(sessionId).subscribe(
        (response) => {
          console.log('Training session deleted successfully:', response);
          alert('Training session deleted successfully!');
          
        },
        (error) => {
          console.error('Error deleting training session:', error);
          alert('Failed to delete the training session. Please try again.');
        }
      );
    }
  }
  
  logout(): void {
    sessionStorage.clear(); 
    this.router.navigate(['/login']); 
  }
}
