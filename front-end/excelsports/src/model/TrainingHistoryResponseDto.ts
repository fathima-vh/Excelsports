export class TrainingHistoryResponseDto {
    sessionId: number;
    date: string;
    duration: number;
    athleteId: number;
    totalCaloriesBurned: number;
    categorizedExercises: Map<string, ExerciseSummary[]>; 
  
    constructor(
      sessionId: number,
      date: string,
      duration: number,
      athleteId: number,
      totalCaloriesBurned: number,
      categorizedExercises: Map<string, ExerciseSummary[]>
    ) {
      this.sessionId = sessionId;
      this.date = date;
      this.duration = duration;
      this.athleteId = athleteId;
      this.totalCaloriesBurned = totalCaloriesBurned;
      this.categorizedExercises = categorizedExercises;
    }
  }
  
  export class ExerciseSummary {
    exerciseName: string;
    currentValue: number;
    caloriesBurned: number;  
  
    constructor(exerciseName: string, currentValue: number, caloriesBurned: number) {
      this.exerciseName = exerciseName;
      this.currentValue = currentValue;
      this.caloriesBurned = caloriesBurned;
    }
  }
  