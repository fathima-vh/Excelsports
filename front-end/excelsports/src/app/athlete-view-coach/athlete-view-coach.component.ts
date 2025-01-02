import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Coach } from '../../model/Coach';
import { MessageDto } from '../../model/MessageDto';
import { AthleteHeaderComponent } from '../athlete-header/athlete-header.component';
import { AthleteMessageComponent } from '../athlete-message/athlete-message.component';
import { AthleteService } from '../athlete.service';
import { AuthService } from '../auth.service';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-athlete-view-coach',
  imports:[CommonModule,FormsModule,AthleteHeaderComponent],
  templateUrl: './athlete-view-coach.component.html',
  styleUrls: ['./athlete-view-coach.component.css']
})
export class AthleteViewCoachComponent implements OnInit {
  currentCoach: any = null;
  athleteId: number = 0;
  coachId: number = 0;
  messages: MessageDto[] = [];
  messageContent: string = '';
  loading: boolean = false;
  errorMessage: string | null = null;
  showChat: boolean = false;
  availableCoaches: Coach[]=[];

  constructor(
    private athleteService: AthleteService,
    private messageService: MessageService,
    private router:Router
  ) {}

  ngOnInit(): void {
    this.athleteId = Number(sessionStorage.getItem('athleteId'));
    this.loadCoachDetails();
  }

  loadCoachDetails(): void {
    this.loading = true;
    this.athleteService.getCoachForAthlete(this.athleteId).subscribe({
      next: (coach) => {
        this.currentCoach = coach;
        this.coachId = coach.id;
        this.loadMessages();
        this.loading = false;
      },
      error: (err) => {
        console.warn('No coach assigned to this athlete. Loading list of available coaches.');
        this.loadAllCoaches();
      },
    });
  }

  loadAllCoaches(): void {
    this.athleteService.getAllCoaches().subscribe({
      next: (coaches) => {
        this.availableCoaches = coaches || []; // Fallback to an empty array
        this.loading = false;
      },
      error: (err) => {
        this.errorMessage = 'Failed to load available coaches.';
        this.availableCoaches = []; // Ensure it’s an empty array in case of an error
        this.loading = false;
      },
    });
  }
  

  loadMessages(): void {
    this.messageService.getMessagesOfCoachAndAthlete(this.athleteId, this.coachId).subscribe({
      next: (messages) => {
        console.log("Loaded messages:", messages);
        
        this.messages = (messages || []).sort((b, a) => new Date(b.sentAt).getTime() - new Date(a.sentAt).getTime());
      },
      error: () => {
        this.errorMessage = 'Failed to load messages.';
      },
    });
  }
  

  toggleChat(): void {
    this.showChat = !this.showChat;
  }

  sendMessage(): void {
    if (!this.messageContent.trim()) return;
  
    const message: MessageDto = {
      athleteId: this.athleteId,
      coachId: this.coachId,
      content: this.messageContent.trim(),
      sentAt: new Date(),
      role: 'ATHLETE', // Explicitly setting role
      read: false,
    };
  
    this.messageService.sendMessage(message).subscribe({
      next: (response: MessageDto) => {
        if (!response) {
          console.error('Error: Received empty or null response from the backend');
          return;
        }
        const newMessage = {
          ...response,
          role: response.role || 'ATHLETE', // Fallback if role is missing
          sentAt: response.sentAt || new Date(), // Ensure sentAt is not missing
        };
  
        // Add the new message to the array for immediate display
        this.messages.push(newMessage);
  
        // Clear the input field after sending
        this.messageContent = '';
      },
      error: (err) => {
        console.error('Error sending message:', err);
        this.errorMessage = 'Failed to send message.';
      },
    });
  }
  
  
  

  sendRequestToCoach(coachId: number): void {
    const athleteId = this.athleteService.getAthleteId();

    if (coachId) {
      this.athleteService.requestCoach(coachId).subscribe(
        (response) => {
          // alert('Request sent successfully');
          this.router.navigate(['/athlete-home']);
        },
        (error) => {
          alert(error);  // Display the error message from backend
          console.error(error);  // Log the error for debugging
        }
      );
    }
  }
}