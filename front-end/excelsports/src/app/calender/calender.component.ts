import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FullCalendarModule } from '@fullcalendar/angular';
import { CalendarOptions } from '@fullcalendar/core';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import { AthleteHeaderComponent } from '../athlete-header/athlete-header.component';
import { TrainingSessionService } from '../training-session.service';

interface CalendarEvent {
  date: string;
  totalCalories: number;
  percentage: number;
}

@Component({
  selector: 'app-calender',
  imports: [FullCalendarModule, AthleteHeaderComponent, CommonModule],
  templateUrl: './calender.component.html',
  styleUrls: ['./calender.component.css']
})
export class CalenderComponent implements OnInit {

  calorieHistory: any[] = [];
  calorieGoal: number = Number(sessionStorage.getItem('goalCalorie')) || 2000; 
  calendarEvents: CalendarEvent[] = [];
  calendarOptions: CalendarOptions = {
    plugins: [dayGridPlugin, interactionPlugin],
    initialView: 'dayGridMonth',
    headerToolbar: {
      left: 'prev,next today',
      center: 'title',
      right: 'dayGridMonth,dayGridWeek,dayGridDay'
    },
    // dateClick: this.handleDateClick.bind(this), 
    events: [], 
    eventContent: this.renderEventContent.bind(this) 
  };

  constructor(private sessionService: TrainingSessionService) {}

  ngOnInit(): void {
    const athleteId = Number(sessionStorage.getItem('athleteId')); 
    this.getCaloriesHistory(athleteId);
  }

  getCaloriesHistory(id: number): void {
    this.sessionService.getCalorieHistory(id).subscribe(
      (data) => {
        this.calorieHistory = data;
        this.populateCalendarEvents();
        this.updateCalendarWithEvents();
      },
      (error) => {
        console.error('Error fetching calorie history:', error);
      }
    );
  }

  populateCalendarEvents(): void {
    this.calendarEvents = this.calorieHistory.map((item: any) => {
      const date = item[0]; // Assuming the first item is the date
      const totalCalories = item[1]; // Assuming the second item is total calories
      const percentage = Math.min(Math.round((totalCalories / this.calorieGoal) * 100), 100); 
      return { 
        date, 
        totalCalories, 
        percentage 
      };
    });
  }

  updateCalendarWithEvents(): void {
    this.calendarOptions.events = this.calendarEvents.map(event => ({
      start: event.date,
      extendedProps: { 
        percentage: event.percentage,
        totalCalories: event.totalCalories
      }
    }));
  }

  // handleDateClick(arg: any): void {
  //   alert('Date clicked: ' + arg.dateStr);
  // }

  // Custom render function for FullCalendar to show smaller circular progress (SVG)
  renderEventContent(eventInfo: any): { html: string } {
    const percentage = eventInfo.event.extendedProps.percentage || 0;
    const totalCalories = eventInfo.event.extendedProps.totalCalories || 0;
    const radius = 70; 
    const strokeWidth = 15; 
    const circumference = 2 * Math.PI * radius; 
    const strokeDasharray = `${(percentage / 100) * circumference}, ${circumference}`; 
  
    return {
  html: `
    <div class="calendar-ring">
      <svg viewBox="0 0 200 200">
        <!-- Background Circle with no fill -->
        <circle r="${radius}" cx="50%" cy="50%" stroke="white" fill="none" stroke-width="${strokeWidth}"></circle>

        <!-- Progress Circle with Animation -->
        <circle r="${radius}" cx="50%" cy="50%" 
                stroke="${percentage >= 100 ? '#00FF00' : 'red'}" 
                fill="none" stroke-width="${strokeWidth}" 
                stroke-linecap="round" 
                stroke-dasharray="${strokeDasharray}" 
                class="progress-ring">
        </circle>
      </svg>

      <div class="percentage-text">${percentage}%</div>
    </div>
  `
};

    
  }
  
}
