import { CommonModule } from '@angular/common';
import { Input, OnInit } from '@angular/core';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Exercise } from '../../model/Exercise';
import { Goal } from '../../model/Goal';
import { CategoryService } from '../category.service';
import { CoachHeaderComponent } from '../coach-header/coach-header.component';
import { ExerciseService } from '../exercise.service';
import { GoalService } from '../goal.service';

@Component({
  selector: 'app-coach-set-goals',
  imports: [CommonModule,FormsModule,CoachHeaderComponent,ReactiveFormsModule],
  templateUrl: './coach-set-goals.component.html',
  styleUrl: './coach-set-goals.component.css'
})
export class CoachSetGoalsComponent implements OnInit {
  goalForm: FormGroup;
  athleteId: number = 0;
  goals: any[] = [];
  categories: string[] = [];
  exercises: string[] = [];
  editingGoalId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private goalService: GoalService,
    private route: ActivatedRoute,
    private categoryService: CategoryService,
    private exerciseService: ExerciseService
  ) {
    this.goalForm = this.fb.group({
      selectedCategory: ['', Validators.required],
      selectedExercise: [{ value: '', disabled: true }, Validators.required], // Initialize as disabled
      currentExerciseValue: [null, [Validators.required, Validators.min(1)]],
    });
  }

  ngOnInit(): void {
    this.athleteId = +this.route.snapshot.paramMap.get('athleteId')!;
    
    this.goalForm = this.fb.group({
      selectedCategory: ['', Validators.required],
      selectedExercise: [{ value: '', disabled: true }, Validators.required], // Initialize as disabled
      currentExerciseValue: [null, [Validators.required, Validators.min(1)]],
    });
  
    this.loadCategories();
    this.fetchGoals();
  }
  

  loadCategories() {
    this.categoryService.getAllCategories().subscribe((categories) => {
      this.categories = categories;
    });
  }

  onCategoryChange(): void {
    const selectedCategory = this.goalForm.get('selectedCategory')?.value;
    if (selectedCategory) {
      this.categoryService.getExercisesByCategory(selectedCategory).subscribe(
        (exercises) => {
          this.exercises = exercises;
          this.goalForm.get('selectedExercise')?.enable(); // Enable control
        },
        (error) => {
          console.error('Error fetching exercises:', error);
          this.exercises = [];
          this.goalForm.get('selectedExercise')?.disable(); // Disable control on error
        }
      );
    } else {
      this.goalForm.get('selectedExercise')?.disable(); // Disable control if no category selected
    }
  }
  

  onSubmit(): void {
    if (this.goalForm.invalid) {
      alert('Please fill out all fields correctly.');
      return;
    }

    const goalData = {
      athleteId: this.athleteId,
      targetCategory: this.goalForm.value.selectedCategory,
      targetExerciseName: this.goalForm.value.selectedExercise,
      targetValue: this.goalForm.value.currentExerciseValue,
    };

    if (this.editingGoalId) {
      // If editing an existing goal, call the update API
      this.goalService.updateGoal(this.editingGoalId, goalData).subscribe({
        next: () => {
          // alert('Goal updated successfully!');
          this.fetchGoals();
          this.goalForm.reset();
          this.editingGoalId = null;  // Reset after updating
        },
        error: (err) => {
          console.error('Error updating goal:', err);
          alert('Failed to update goal.');
        },
      });
    } else {
      // If creating a new goal, call the create API
      this.goalService.createGoal(goalData).subscribe({
        next: () => {
          // alert('Goal added successfully!');
          this.fetchGoals();
          this.goalForm.reset();
        },
        error: (err) => {
          console.error('Error creating goal:', err);
          alert('Failed to add goal.');
        },
      });
    }
  }

  fetchGoals(): void {
    this.goalService.getGoals(this.athleteId).subscribe({
      next: (goals) => {
        this.goals = goals;
      },
      error: (err) => {
        console.error('Error fetching goals:', err);
      },
    });
  }

  editGoal(goal: any): void {
    this.editingGoalId = goal.id;  // Set the goal ID for editing
    this.goalForm.patchValue({
      selectedCategory: goal.targetCategory,
      selectedExercise: goal.targetExerciseName,
      currentExerciseValue: goal.targetValue,
    });
    this.onCategoryChange();  // Update exercises based on selected category
  }

  deleteGoal(goalId: number): void {
    if (confirm('Are you sure you want to delete this goal?')) {
      this.goalService.deleteGoal(goalId).subscribe({
        next: () => {
          // alert('Goal deleted successfully!');
          this.fetchGoals();
        },
        error: (err) => {
          console.error('Error deleting goal:', err);
          alert('Failed to delete goal.');
        },
      });
    }
  }
   currentScrollIndex = 0;

  scrollLeft() {
    const container = document.querySelector('.goal-cards-container') as HTMLElement;
    const cardWidth = 320; // Adjust this based on your card width and gap
    if (this.currentScrollIndex > 0) {
      this.currentScrollIndex--;
      container.style.transform = `translateX(-${this.currentScrollIndex * cardWidth}px)`;
    }
  }
  
  scrollRight() {
    const container = document.querySelector('.goal-cards-container') as HTMLElement;
    const cardWidth = 320; // Adjust this based on your card width and gap
    if (this.currentScrollIndex < this.goals.length - 1) {
      this.currentScrollIndex++;
      container.style.transform = `translateX(-${this.currentScrollIndex * cardWidth}px)`;
    }
  }
  
}