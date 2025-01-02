import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CalorieHistory } from '../model/CalorieHistory';
import { TrainingHistoryResponseDto } from '../model/TrainingHistoryResponseDto';
import { TrainingSession } from '../model/TrainingSession';
import { ViewTrainingSession } from '../model/ViewTrainingSession';

@Injectable({
  providedIn: 'root'
})
export class TrainingSessionService {

  private baseUrl = 'http://localhost:8888/api/v1/training-sessions'; 

  constructor(private http: HttpClient) {}

  // 🔒 Helper function to get headers with the token
  private getAuthHeaders(): HttpHeaders {
    const token = sessionStorage.getItem('token');
    if (!token) throw new Error('No token found in sessionStorage');
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }

  addTrainingSession(trainingSession: TrainingSession): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post<any>(`${this.baseUrl}`, trainingSession, { headers });
  }

  updateTrainingSession(trainingSession: TrainingSession): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.put<any>(`${this.baseUrl}/update/${trainingSession.id}`, trainingSession, { headers });
  }

  viewTrainingSession(athleteId: number): Observable<ViewTrainingSession[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<ViewTrainingSession[]>(`${this.baseUrl}/athlete/${athleteId}`, { headers });
  }

  getTrainingSessionBySessionId(sessionId: number): Observable<ViewTrainingSession> {
    const headers = this.getAuthHeaders();
    return this.http.get<ViewTrainingSession>(`${this.baseUrl}/${sessionId}`, { headers });
  }

  deleteTrainingSession(trainingSessionID: number): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.delete<any>(`${this.baseUrl}/${trainingSessionID}`, { headers });
  }

  getLatestSession(athleteId: number): Observable<ViewTrainingSession> {
    const headers = this.getAuthHeaders();
    return this.http.get<ViewTrainingSession>(`${this.baseUrl}/athlete/${athleteId}/latest-training-session`, { headers });
  }
  getTrainingHistory(
    athleteId: number,
    year: number,
    month?: number,
    day?: number
  ): Observable<TrainingHistoryResponseDto[]> {
    const params: any = { year };
    if (month) params.month = month;
    if (day) params.day = day;
    const headers = this.getAuthHeaders();
// console.log('Auth Token:', token); // Log the token
// console.log('Headers:', JSON.stringify(headers.keys(), null, 2)); // Log header keys
// if (!token) throw new Error('No token found in sessionStorage');
return this.http.get<TrainingHistoryResponseDto[]>(
  `${this.baseUrl}/athlete/${athleteId}/training-history`,
  { headers, params }
);
  }

  getCalorieHistory(id: number): Observable<CalorieHistory[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<CalorieHistory[]>(`${this.baseUrl}/athlete/${id}/calorie-history`, { headers });
  }
  
  

}

