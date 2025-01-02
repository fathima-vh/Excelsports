import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AthleteHeaderComponent } from '../athlete-header/athlete-header.component';
import { TrainingSessionService } from '../training-session.service';

@Component({
  selector: 'app-athlete-add-training-session',
  imports: [CommonModule,ReactiveFormsModule,AthleteHeaderComponent],
  templateUrl: './athlete-add-training-session.component.html',
  styleUrl: './athlete-add-training-session.component.css'
})
export class AthleteAddTrainingSessionComponent implements OnInit {
  trainingSessionForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private trainingSessionService: TrainingSessionService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.trainingSessionForm = this.fb.group({
      date: ['', Validators.required],
      duration: [0, [Validators.required, Validators.min(1)]],
    });
  }

  submitForm(): void {
    if (this.trainingSessionForm.valid) {
      const trainingSession = {
        ...this.trainingSessionForm.value,
        athleteId: Number(sessionStorage.getItem('athleteId')), // Assume athleteId is stored in sessionStorage
      };

      this.trainingSessionService.addTrainingSession(trainingSession).subscribe(
        (response) => {
          // alert('Training session added successfully!');
          this.router.navigate(['athlete-edit-training-session/',response.id]);
        },
        (error) => {
          console.error("Error : ",error);
         alert(error.error.message);
          
        }
      );
     }
  }
}
