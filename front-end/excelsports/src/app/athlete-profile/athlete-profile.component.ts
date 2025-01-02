import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Athlete } from '../../model/Athlete';
import { AthleteHeaderComponent } from '../athlete-header/athlete-header.component';
import { AthleteService } from '../athlete.service';

@Component({
  selector: 'app-athlete-profile',
  imports: [AthleteHeaderComponent, CommonModule,FormsModule],
  templateUrl: './athlete-profile.component.html',
  styleUrls: ['./athlete-profile.component.css']
})
export class AthleteProfileComponent implements OnInit {
  athleteDetails: Athlete | null = null;
  isEditing: boolean = false; // Flag to toggle edit mode

  constructor(private athleteService: AthleteService, private router: Router) {}

  ngOnInit(): void {
    const email = sessionStorage.getItem('email'); 
    if (email) {
      this.fetchAthleteDetails(email);
    } else {
      alert('No athlete is logged in!');
      this.router.navigate(['/login']);
    }
  }

  fetchAthleteDetails(email: string): void {
    this.athleteService.getAthleteDetails(email).subscribe(
      (data: Athlete) => {
        this.athleteDetails = data;
        this.athleteService.setAthleteId(data.id);
        console.log('Athlete details fetched successfully:', data);
        sessionStorage.setItem('athleteId', data.id.toString());
      },
      (error) => {
        console.error('Error fetching athlete details:', error);
        alert('Failed to load athlete details. Please try again later.');
      }
    );
  }

  toggleEdit(): void {
    if (this.isEditing) {
      this.updateAthleteDetails();
    }
    this.isEditing = !this.isEditing; // Toggle the edit mode
  }

  updateAthleteDetails(): void {
    if (!this.athleteDetails) {
      return;
    }

    this.athleteService.updateAthleteDetails(this.athleteDetails).subscribe(
      (response) => {
        console.log('Athlete details updated successfully:', response);
        // alert('Profile updated successfully.');
        this.isEditing = false; // End edit mode after a successful update
      },
      (error) => {
        console.error('Error updating athlete details:', error);
        alert('Failed to update athlete details. Please try again later.');
      }
    );
  }

  logout(): void {
    sessionStorage.clear(); 
    this.router.navigate(['/login']); 
  }
}
