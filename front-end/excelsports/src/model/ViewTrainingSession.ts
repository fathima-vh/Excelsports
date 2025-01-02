import { Exercise } from "./Exercise";

export interface ViewTrainingSession {
    
    id: number;
    date:string;
    duration:number;
    athleteId:number;
    exercises:Exercise[];
    caloriesBurned:number;

}