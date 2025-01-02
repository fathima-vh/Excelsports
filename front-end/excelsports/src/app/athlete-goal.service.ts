import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Goal } from '../model/Goal';

@Injectable({
  providedIn: 'root'
})
export class AthleteGoalService {
  private baseUrl = 'http://localhost:8888/api/v1/goals'; 

  constructor(private http: HttpClient) {}


  viewAthleteGoals(athleteId:number) : Observable<Goal[]>{
    athleteId = Number(sessionStorage.getItem('athleteId'));
    const token = sessionStorage.getItem('token');
    const id = Number(sessionStorage.getItem('athleteId'));
    if (!token) {
      throw new Error('No token found in sessionStorage');
    }
    
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.http.get<Goal[]>(`${this.baseUrl}/athlete/${athleteId}`, { headers });
  }

}
