import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Exercise } from '../model/Exercise';

@Injectable({
  providedIn: 'root'
})
export class ExerciseService {

  private baseUrl = 'http://localhost:8888/api/v1/exercises'; 

  constructor(private http: HttpClient) {}

  addExercises(exercise:Exercise){

    
    const token = sessionStorage.getItem('token');
    const id = Number(sessionStorage.getItem('athleteId'));
    if (!token) {
      throw new Error('No token found in sessionStorage');
    }
    
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
  
    console.log("exercise in service: ",exercise);
  
    return this.http.post<any>(`${this.baseUrl}`,exercise, { headers } );

  }

  deleteExercise(exerciseId:number){

    
    const token = sessionStorage.getItem('token');
    const id = Number(sessionStorage.getItem('athleteId'));
    if (!token) {
      throw new Error('No token found in sessionStorage');
    }
    
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
  
    console.log("exercise id  in service: ",exerciseId);
  
    return this.http.delete<any>(`${this.baseUrl}/${exerciseId}`, { headers } );

  }


  getExerciseDetails(exerciseName: string): Observable<any> {
    return this.http.get<any>(`https://exercisedb.p.rapidapi.com/exercises/name/${exerciseName}`, {
      headers: {
        'x-rapidapi-key': '6a70a9e56cmsh8ba731c614500a6p1d94bcjsncda051bc1f97', // Replace with your API key
        'x-rapidapi-host': 'exercisedb.p.rapidapi.com'
      }
    });
  }

}
