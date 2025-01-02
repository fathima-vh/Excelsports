import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Athlete } from '../../model/Athlete';
import { Message } from '../../model/Message';
import { MessageDto } from '../../model/MessageDto';
import { AthleteService } from '../athlete.service';
import { CoachHeaderComponent } from '../coach-header/coach-header.component';
import { CoachService } from '../coach.service';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-coach-message',
  imports: [CommonModule,FormsModule,CoachHeaderComponent],
  templateUrl: './coach-message.component.html',
  styleUrl: './coach-message.component.css'
})
export class CoachMessageComponent implements OnInit{
  athletes: Athlete[] = []; 
  selectedAthleteId: number | null = null; 
  messageContent: string = ''; 
  coachId: number = 0; 
  athleteName:string='';
  loading: boolean = true; 
  errorMessage: string | null = null; 
  messages: MessageDto[] = []; 
  id:number=0;
  selectedSortOption: string='time-asc';
  filteredMessages: MessageDto[]=[]; 
  searchTerm: string = ''; 


  constructor(
    private http: HttpClient,
    private coachService: CoachService,
    private athleteService: AthleteService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    
    this.coachId=Number(sessionStorage.getItem('coachId'));
    console.log('Coach ID:', this.coachId); 
    

    this.loadMessagesForCoach();
    this.loadAthletes(); 
    this.filteredMessages = this.messages;
  }
  loadMessagesForCoach(): void {
    this.loading = true;
    this.messageService.getMessagesForCoach(this.coachId).subscribe({
      next: (messages: MessageDto[]) => {
        this.messages = messages;
        this.filteredMessages = messages;  // Set filteredMessages to all messages initially
        console.log(messages);
        this.loading = false;
      },
      error: (err) => {
        this.errorMessage = 'Failed to load messages. Please try again later.';
        this.loading = false;
      }
    });
  }
  
  
  
  markAsRead(id: number | undefined): void {
    if (id === undefined) {
      console.error('Message ID is undefined. Cannot mark as read.');
      return;
    }
    this.messageService.markMessageAsRead(id).subscribe({
      next: () => {
        this.messages = this.messages.map(msg => 
          msg.id === id ? { ...msg, read: true } : msg
        );
      },
      error: (err) => {
        console.error('Failed to mark message as read:', err);
      }
    });
  }
  
  deleteMessage(id: number | undefined): void {
    if (id === undefined) {
      console.error('Message ID is undefined. Cannot delete.');
      return;
    }
    this.messageService.deleteMessage(id).subscribe({
      next: () => {
        this.messages = this.messages.filter(msg => msg.id !== id);
      },
      error: (err) => {
        console.error('Failed to delete message:', err);
      }
    });
  }
  
  
  
  sortMessages(field: string, order: string) {
    if (field === 'time') {
      this.messages.sort((a, b) => {
        const timeA = new Date(a.sentAt).getTime();
        const timeB = new Date(b.sentAt).getTime();
        return order === 'asc' ? timeA - timeB : timeB - timeA;
      });
    } else if (field === 'read') {
      this.messages.sort((a, b) => {
        return order === 'asc' ? (a.read ? 1 : -1) : (a.read ? -1 : 1);
      });
    }
  }
  
  
  loadAthletes(): void {
    this.loading = true;
    this.coachService.getAthletesByCoachId(this.coachId).subscribe({
      next: (athletelist: Athlete[]) => {
        this.athletes = athletelist;
        this.loading = false; 
      },
      error: (err) => {
        this.errorMessage = 'Unable to fetch athletes. Please try again later.';
        this.loading = false; 
      }
    });
  }

  sendMessage(): void {
    if (this.selectedAthleteId && this.messageContent.trim()) {
      const message: MessageDto = {
        athleteId: this.selectedAthleteId,
        coachId: this.coachId,
        content: this.messageContent.trim(),
        sentAt: new Date(),
        role: 'COACH', 
        read: false,
      };
  
      this.messageService.sendMessage(message).subscribe({
        next: (response: MessageDto) => {
          this.messages.push(response); 
          this.messageContent = '';
          this.selectedAthleteId = null;
  
          
        },
        error: (err) => {
          console.error('Error sending message:', err);
          alert('Failed to send the message. Please try again later.');
        },
      });
    } else {
      alert('Please select an athlete and enter a message.');
    }
  }
  
  filterMessages(): void {
    if (!this.searchTerm) {
      this.filteredMessages = [...this.messages];
    } else {
      const term = this.searchTerm.toLowerCase();
      this.filteredMessages = this.messages.filter(message =>
        message.athleteName?.toLowerCase().includes(term) ||
        message.content.toLowerCase().includes(term)
      );
    }
  }
  
  
}