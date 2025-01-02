import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from '../../model/Category';
import { Exercise } from '../../model/Exercise';
import { ViewTrainingSession } from '../../model/ViewTrainingSession';
import { AthleteHeaderComponent } from '../athlete-header/athlete-header.component';
import { CategoryService } from '../category.service';
import { ExerciseService } from '../exercise.service';
import { TrainingSessionService } from '../training-session.service';

@Component({
  selector: 'app-athlete-edit-training-session',
  imports: [CommonModule,FormsModule,AthleteHeaderComponent],
  templateUrl: './athlete-edit-training-session.component.html',
  styleUrl: './athlete-edit-training-session.component.css'
})
export class AthleteEditTrainingSessionComponent implements OnInit {
  trainingsessionId: number | null = null;
  // trainingSession: ViewTrainingSession | null = null;
  newExercise: string = '';
  selectedCategory: string | null = null;
  selectedExercise: string | null = null;
  categories: string[] = [];  
  exercises: string[] = []; 

  trainingSession: ViewTrainingSession = {
    id: 0,
    date: '',
    duration: 0,
    athleteId:0,
    exercises: [], 
    caloriesBurned:0
  };

  currentExerciseValue:number|null = null;

  constructor(
    private route: ActivatedRoute,
    private sessionService: TrainingSessionService,
    private categoryService: CategoryService,
    private exerciseService: ExerciseService,
    private router:Router,
  ) {}

  ngOnInit(): void {
    this.trainingsessionId = Number(this.route.snapshot.paramMap.get('id'));
    if (this.trainingsessionId) {
      this.loadTrainingSession();
    }
    this.loadCategories();  // Load categories when component initializes
  }

  loadTrainingSession() {
    this.sessionService.getTrainingSessionBySessionId(this.trainingsessionId!).subscribe(
      (data: ViewTrainingSession) => {
        this.trainingSession = data;
      },
      (error) => {
        console.error('Error fetching session details:', error);
        alert('Failed to load session details. Please try again later.');
      }
    );
  }

  loadCategories() {
    
    this.categoryService.getAllCategories().subscribe((categories: string[]) => {
      this.categories = categories;
    });
  }

  onCategoryChange() {
    if (this.selectedCategory) {
      this.categoryService.getExercisesByCategory(this.selectedCategory).subscribe(
        (exercies: string[]) => {
          this.exercises = exercies;
        }
      );
    }
  }

  addExercise() {
    if (this.selectedCategory && this.selectedExercise && this.currentExerciseValue != null) {
      // Create a new Exercise object
      const newExercise = new Exercise(
        0,
        this.trainingsessionId!,  
        this.selectedCategory,  
        this.selectedExercise,  
        this.currentExerciseValue  
      );
  
      // Call the service to save the exercise
      console.log("exercise in component : ",newExercise);
      
      this.exerciseService.addExercises(newExercise).subscribe(
        (response) => {
          console.log('Exercise added:', response);
          // alert('Exercise added successfully!');
          
          // Optionally, update the training session with the new exercise
          // if you want to keep track of the exercises in the frontend.
          if (this.trainingSession) {
            this.trainingSession.exercises.push(newExercise);
          }
  
          
          this.selectedCategory = null;
          this.selectedExercise = null;
          this.currentExerciseValue = null;
        },
        (error) => {
          console.error('Error adding exercise:', error);
          alert('Failed to add exercise. Please try again.');
        }
      );
    } else {
      alert('Please select a category, exercise, and enter a valid value.');
    }
  }
  
  removeExercise(exerciseId: number) {
    this.exerciseService.deleteExercise(exerciseId).subscribe(
      (response) => {
        console.log('Exercise deleted:', response);
        // alert('Exercise deleted successfully!');
      },
      (error) => {
        console.error('Error deleting exercise:', error);
        alert('Failed to delete exercise. Please try again.');
      }
    );
  
}

saveChanges() {
  if (this.trainingSession) {
    this.sessionService.updateTrainingSession(this.trainingSession).subscribe(
      (response) => {
        console.log('Training session updated successfully:', response);
        // alert('Training session saved successfully!');
        this.router.navigate(['/athlete-view-sessions', this.trainingSession.athleteId]);
      },
      (error) => {
        console.error('Error updating training session:', error);
        alert('Failed to save changes. Please try again.');
      }
    );
  } else {
    alert('No training session loaded to save changes.');
  }
}

}