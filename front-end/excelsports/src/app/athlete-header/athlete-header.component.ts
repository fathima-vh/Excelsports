import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { Athlete } from '../../model/Athlete';
import { AthleteService } from '../athlete.service';

@Component({
  selector: 'app-athlete-header',
  imports:[CommonModule,RouterLink],
  templateUrl: './athlete-header.component.html',
  styleUrls: ['./athlete-header.component.css']
})
export class AthleteHeaderComponent {

  @Input() athlete: Athlete | null = null;
  
  isSidebarOpen = false; 

  constructor(private router: Router, private athleteService:AthleteService) {}

  toggleSidebar() {
    this.isSidebarOpen = !this.isSidebarOpen;
  }


  logout() {
    
    sessionStorage.clear();
    
    this.router.navigate(['/login']);
  }

  viewCoach(): void {
    if (this.athlete) {
      this.athleteService.setAthleteId(this.athlete.id); 
      this.router.navigate(['/athlete-view-coach', this.athlete.id]); 
    } else {
      console.error('Athlete data is not available');
    }
  }
}
