import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Input, OnInit } from '@angular/core';
import { Component } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Observable } from 'rxjs';
import { Coach } from '../../model/Coach';
import { CoachService } from '../coach.service';

@Component({
  selector: 'app-coach-header',
  imports:[CommonModule,RouterLink],
  templateUrl: './coach-header.component.html',
  styleUrls: ['./coach-header.component.css'],
})
export class CoachHeaderComponent {

  @Input() coach:Coach|null=null;

  isSidebarOpen = false;

  constructor(private router: Router,private coachService:CoachService,private route: ActivatedRoute) {}

  toggleSidebar() {
    this.isSidebarOpen = !this.isSidebarOpen;
    document.body.style.overflow = this.isSidebarOpen ? 'hidden' : 'auto'; // Prevent scrolling when sidebar is open
  }

  logout() {
    sessionStorage.clear();
    this.router.navigate(['/login']);
  }

  viewAthletes(): void {
    // console.log("hi ",this.coach);    
    if (this.coach ) {
      // If coach email is available, set the coach email to navigate
      this.coachService.setCoachId(this.coach.id);
      console.log("view athletes : ",this.coach.id);
      // this.router.navigate(['/coach-athletes', this.coach.id]); 
      
      
      // Pass the coach email or ID to the route
    }
    //  else {
    //   console.error('Coach data is not available');
    // }
  }
  viewRequests():void{
    console.log(this.coach?.id);
    
    if (this.coach ) {
      // If coach email is available, set the coach email to navigate
      this.coachService.setCoachId(this.coach.id);
      
      // this.router.navigate(['/coach-view-requests']);  // Pass the coach email or ID to the route
    } 
    // else {
    //   console.error('Request data is not available');
    // }
  }

  message():void{
    if(this.coach){
      this.coachService.setCoachId(this.coach.id);
      // this.router.navigate(['/coach-message',this.coach.id]);
    }
  }

}
