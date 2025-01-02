import { CommonModule } from '@angular/common';
import { AfterViewInit } from '@angular/core';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Coach } from '../../model/Coach';
import { CoachHeaderComponent } from '../coach-header/coach-header.component';
import { CoachService } from '../coach.service';

@Component({
  selector: 'app-coach-profile',
  imports: [CoachHeaderComponent, CommonModule,FormsModule],
  templateUrl: './coach-profile.component.html',
  styleUrls: ['./coach-profile.component.css']
})
export class CoachProfileComponent implements OnInit{
  coach: Coach | null = null;
  isEditing: boolean = false; 

  constructor(
    private coachService: CoachService,
    private router: Router,
    private cdRef: ChangeDetectorRef
    ) {}

  ngOnInit(): void {
    
    const email = sessionStorage.getItem('email'); 
    if (email) {
      this.fetchCoachDetails(email);
    } else {
      alert('No coach is logged in!');
      this.router.navigate(['/login']);
    }
    this.cdRef.detectChanges();
  }

  fetchCoachDetails(email: string): void {
    this.coachService.getCoachDetails(email).subscribe(
      (data: Coach) => {
        this.coach = data;
        sessionStorage.setItem('coachId', this.coach.id.toString());
        console.log('Coach details fetched successfully:', data);
      },
      (error) => {
        console.error('Error fetching coach details:', error);
        alert('Failed to load coach details. Please try again later.');
      }
    );
  }

  toggleEdit(): void {
    if (this.isEditing) {
      this.updateCoachDetails();
    }
    this.isEditing = !this.isEditing; // Toggle the edit mode
  }

  updateCoachDetails(): void {
    if (!this.coach) {
      return;
    }

    this.coachService.updateCoachDetails(this.coach).subscribe(
      (response) => {
        console.log('Coach details updated successfully:', response);
        alert('Profile updated successfully.');
        this.isEditing = false; // End edit mode after a successful update
      },
      (error) => {
        console.error('Error updating coach details:', error);
        alert('Failed to update coach details. Please try again later.');
      }
    );
  }

  logout(): void {
    sessionStorage.clear(); 
    this.router.navigate(['/login']);
  }
}
