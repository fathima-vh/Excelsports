import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Athlete } from '../../model/Athlete';
import { CoachService } from '../coach.service';
import {CoachRequestsDto} from '../../model/CoachRequestsDto';
import { CoachHeaderComponent } from '../coach-header/coach-header.component';

@Component({
  selector: 'app-coach-view-req',
  imports: [CommonModule,CoachHeaderComponent],
  templateUrl: './coach-view-req.component.html',
  styleUrl: './coach-view-req.component.css'
})
// export class CoachViewReqComponent {
//   coach: any; // Define the coach property
//   coachId:number=0;
//   athletes: CoachRequestsDto[] = [];
//   errorMessage: string | null = null;
//   loading: boolean=false;

//   constructor(
//     private coachService: CoachService,
//     private route: ActivatedRoute,
//     private router:Router
//   ) {}
//   ngOnInit():void{
//     this.coachId=this.coachService.getCoachId();
//     this.fetchAthleteRequests();
//   }
//   fetchAthleteRequests(): void {
//     this.loading = true;
//     this.coachService.viewRequests(this.coachId).subscribe({
//       next: (athletelist: CoachRequestsDto[]) => {
//         this.athletes = athletelist;
//         this.loading = false;
//       },
//       error: (err) => {
//         this.errorMessage = 'Unable to fetch athlete requests. Please try again later.';
//         this.loading = false;
//       }
//     });
   
//   }
//   acceptRequest(requestId: number): void {
//     this.coachService.respondToRequest({ requestId }, 'ACCEPT').subscribe({
//       next: (response) => {
//         console.log('Request accepted successfully:', response);
//         this.fetchAthleteRequests(); // Refresh the list
//       },
//       error: (err) => {
//         console.error('Error accepting request:', err);
//       },
//     });
//   }
  
//   declineRequest(requestId: number): void {
//     this.coachService.respondToRequest({ requestId }, 'DECLINE').subscribe({
//       next: (response) => {
//         console.log('Request declined successfully:', response);
//         this.fetchAthleteRequests(); // Refresh the list
//       },
//       error: (err) => {
//         console.error('Error declining request:', err);
//       },
//     });
//   }
  
  
// }
export class CoachViewReqComponent {
  coachId: number = 0;
  athletes: CoachRequestsDto[] = [];
  errorMessage: string | null = null;
  loading: boolean = false;

  constructor(
    private coachService: CoachService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    // this.coachId = this.coachService.getCoachId();
    this.coachId=Number(sessionStorage.getItem('coachId'));
    console.log("inside req : ",this.coachId);
    
    this.fetchAthleteRequests();
  }

  fetchAthleteRequests(): void {
    this.loading = true;
    this.coachService.viewRequests(this.coachId).subscribe({
      next: (athletelist: CoachRequestsDto[]) => {
        this.athletes = athletelist;
        this.loading = false;
      },
      error: (err) => {
        this.errorMessage = 'Unable to fetch athlete requests. Please try again later.';
        this.loading = false;
      }
    });
  }

  acceptRequest(requestId: number): void {
    console.log('Request ID:', requestId); 
    this.coachService.respondToRequest(requestId, 'ACCEPT').subscribe({
      next: (response) => {
        console.log('Request accepted successfully:', response);
        this.fetchAthleteRequests(); // Refresh the list of requests
      },
      error: (err) => {
        console.error('Error accepting request:', err);
      }
    });
  }

  declineRequest(requestId: number): void {
    console.log("reqId:"+requestId);
    
    this.coachService.declineRequest(requestId).subscribe({
      next: (response) => {
        console.log('Request declined and deleted successfully:', response);
        // alert('Request declined successfully.');
        this.fetchAthleteRequests(); // Refresh the list of requests
      },
      error: (err) => {
        console.error('Error declining the request:', err);
        alert('Failed to decline the request. Please try again.');
      }
    });
  }
  
  

}

