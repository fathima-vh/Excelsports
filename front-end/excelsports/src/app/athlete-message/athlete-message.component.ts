import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { MessageDto } from '../../model/MessageDto';
import { AthleteHeaderComponent } from '../athlete-header/athlete-header.component';
import { AthleteService } from '../athlete.service';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-athlete-message',
  imports: [CommonModule,FormsModule],
  templateUrl: './athlete-message.component.html',
  styleUrl: './athlete-message.component.css'
})
export class AthleteMessageComponent {
  // @Input() coachId: number; 
  coachId:number=0;
  coachName:string='';
  // id:number=0;
  athleteId: number = 0; 
  messageContent: string = '';
  messages: MessageDto[] = []; // For displaying messages
  loading: boolean = false;
  errorMessage: string = '';

  constructor(private http: HttpClient,private route:ActivatedRoute,
    private messageService:MessageService,
    private athleteService:AthleteService
    ) {}

  ngOnInit(): void {
    this.athleteId = Number(sessionStorage.getItem('athleteId'));

    this.athleteService.getCoachForAthlete(this.athleteId).subscribe(
      (coach) => {
        this.coachId = coach.id; // Assign coachId dynamically
        this.coachName =coach.name;
        console.log("Coach ID assigned:", this.coachId);
      },
      (error) => {
        console.error('Failed to fetch coach data:', error);
      }
    );
    console.log("check:"+this.coachId);

    
  }
  

  sendMessage(): void {
    if (this.coachId && this.messageContent.trim()) {

      console.log("coachId:"+this.coachId);
      const message = {
        athleteId:this.athleteId,
        coachId: this.coachId,
        content: this.messageContent,
        sentAt: new Date(),
        role: 'ATHLETE', // Set role as coach
        read: false 
      };

      this.messageService.sendMessage(message).subscribe({
        next: () => {
          // alert('Message sent successfully!');
          this.messageContent = '';
        },
        error: (err) => {
          alert('Failed to send the message. Please try again later.');
          console.error('Error sending message:', err);
        }
      });
    } else {
      alert('Please select an coach and enter a message.');
    }
  }
 
}
