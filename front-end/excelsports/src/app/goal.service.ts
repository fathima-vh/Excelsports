import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Goal } from '../model/Goal';

@Injectable({
  providedIn: 'root'
})
export class GoalService {
 
  private baseUrl = 'http://localhost:8888/api/v1/goals'; // Adjust the URL
  constructor(private http: HttpClient) {}

  private athleteId!: number;
  private id: number | null = null;

  setAthleteId(id: number): void {

    this.athleteId = id;
  }
 
  getAthleteId(): number {
    return this.athleteId;
  }

  setId(id: number): void {

    this.id = id;
  }
 
  getId(): number| null {
    return this.id;
  }


  createGoal(goalData: any): Observable<any> {
    const token = sessionStorage.getItem('token');  // Get token from sessionStorage
    
    if (!token) {
      throw new Error('No token found in sessionStorage');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.post(`${this.baseUrl}/athlete/${goalData.athleteId}`, goalData,{headers});
  }
 
  getGoals(athleteId: number): Observable<any[]> {
    const token = sessionStorage.getItem('token');  // Get token from sessionStorage
    
    if (!token) {
      throw new Error('No token found in sessionStorage');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any[]>(`${this.baseUrl}/athlete/${athleteId}`,{headers});
  }

  // getAllGoals(coachId:number):Observable<any[]>{
  //   const token = sessionStorage.getItem('token');  // Get token from sessionStorage
    
  //   if (!token) {
  //     throw new Error('No token found in sessionStorage');
  //   }

  //   const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
  //   return this.http.get<any[]>(`${this.baseUrl}`,{headers});
  // }

  updateGoal(id: number, goalData: any): Observable<any> {
    const token = sessionStorage.getItem('token');  // Get token from sessionStorage
    
    if (!token) {
      throw new Error('No token found in sessionStorage');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.put(`${this.baseUrl}/${id}`, goalData,{headers});
  }

  deleteGoal(id: number): Observable<any> {
    console.log("in gs"+id);
    const token = sessionStorage.getItem('token');  // Get token from sessionStorage
    
    if (!token) {
      throw new Error('No token found in sessionStorage');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.delete(`${this.baseUrl}/${id}`,{headers});
  }

  getAthleteProgress(id:number){
    console.log("in gs"+id);
    const token = sessionStorage.getItem('token');  // Get token from sessionStorage
    
    if (!token) {
      throw new Error('No token found in sessionStorage');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get(`${this.baseUrl}/athlete/${id}/progress`,{headers});
  }

}
