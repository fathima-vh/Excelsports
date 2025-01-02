export class Exercise {
    id:number;
    trainingSessionsId: number;
    category: string;
    exerciseName: string;
    currentValue: number;
    goalId?:number;

  constructor(id:number,sessionId: number, category: string, exerciseName: string, value: number) {
      this.id=id;
    this.trainingSessionsId = sessionId;
    this.category = category;
    this.exerciseName = exerciseName;
    this.currentValue = value;
  }

  }
  