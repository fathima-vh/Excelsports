import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { Coach } from '../../model/Coach';
import { AuthService } from '../auth.service';
import { CoachHeaderComponent } from '../coach-header/coach-header.component';
import { CoachService } from '../coach.service';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-coach-home',
  imports: [CommonModule, FormsModule, CoachHeaderComponent,RouterLink],
  templateUrl: './coach-home.component.html',
  styleUrls: ['./coach-home.component.css']
})
export class CoachHomeComponent implements OnInit {

  coach: Coach | null = null;
  coachId: number = 0;
  pendingRequestsCount: number = 0;
  unreadMessagesCount: number = 0;

  constructor(private coachService: CoachService, private router: Router,private msgService:MessageService) {}

  ngOnInit(): void {
    const email = sessionStorage.getItem('email');
    if (email) {
      this.fetchCoachDetails(email);
    }
  }

  fetchCoachDetails(email: string): void {
    this.coachService.getCoachDetails(email).subscribe(
      (data: Coach) => {
        this.coach = data;
        sessionStorage.setItem('coachId', this.coach.id.toString());
        this.coachId = this.coach.id;
        console.log('Coach details fetched successfully:', data);

       
        this.fetchPendingRequestsCount(this.coachId);
        this.fetchUnreadMessagesCount(this.coachId);
      },
      (error) => {
        console.error('Error fetching coach details:', error);
        alert('Failed to load coach details. Please try again later.');
      }
    );
  }

  fetchPendingRequestsCount(coachId: number): void {
    this.coachService.getNoOfPendingRequests(coachId).subscribe(
      (count: number) => {
        this.pendingRequestsCount = count;
      },
      (error) => {
        console.error('Error fetching pending requests count:', error);
      }
    );
  }

  fetchUnreadMessagesCount(coachId: number): void {
    this.msgService.getCountOfUnreadMessages(coachId).subscribe(
      (count: number) => {
        console.log("in front -> unread count : ",count);
        
        this.unreadMessagesCount = count;
      },
      (error) => {
        console.error('Error fetching unread messages count:', error);
      }
    );
  }
}
