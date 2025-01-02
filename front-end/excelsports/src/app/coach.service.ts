import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Coach } from '../model/Coach';
import { CoachRequestsDto } from '../model/CoachRequestsDto';
import { Goal } from '../model/Goal';

@Injectable({
  providedIn: 'root'
})
export class CoachService {
  
  private baseUrl = 'http://localhost:8888/api/v1/coaches'; // Adjust the URL

  constructor(private http: HttpClient,) {}

  private coachId!: number;

  setCoachId(id: number): void {

    this.coachId = id;
  }
 
  getCoachId(): number {
    return this.coachId;
  }


  getCoachDetails(email: string): Observable<Coach> {
    const token = sessionStorage.getItem('token');  // Get token from sessionStorage
    
    if (!token) {
      throw new Error('No token found in sessionStorage');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<Coach>(`${this.baseUrl}/${email}`, { headers });
  }

  getAthletesByCoachId(coachId: number): Observable<any> {
    const token = sessionStorage.getItem('token');  // Get token from sessionStorage
    if (!token) {
      throw new Error('No token found in sessionStorage');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get(`${this.baseUrl}/athletes/${coachId}`,{headers});
  }

  viewRequests(coachId: number): Observable<CoachRequestsDto[]> {
    console.log("coachID"+coachId);
    
    const token = sessionStorage.getItem('token');  // Get token from sessionStorage
    if (!token) {
      throw new Error('No token found in sessionStorage');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<CoachRequestsDto[]>(`${this.baseUrl}/${coachId}/requests`,{headers});
  }

  updateCoachDetails(coach: Coach): Observable<Coach> {
    
    const token = sessionStorage.getItem('token');  // Get token from sessionStorage
    if (!token) {
      throw new Error('No token found in sessionStorage');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.put<Coach>(`${this.baseUrl}/${coach.id}`,coach,{headers});
  }

  respondToRequest(requestId: number, response: string): Observable<any> {
    this.coachId=Number(sessionStorage.getItem('coachId'));
    const token = sessionStorage.getItem('token'); // Get token from sessionStorage
    if (!token) {
      throw new Error('No token found in sessionStorage');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    const url = `${this.baseUrl}/${this.coachId}/requests/${requestId}?response=${response}`;
    return this.http.post<any>(url, {}, { headers ,responseType:'text' as 'json'}); // Send requestId and response in the URL
  }

  declineRequest(requestId: number): Observable<any> {
    const token = sessionStorage.getItem('token');
    if (!token) {
      throw new Error('No token found in sessionStorage');
    }
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    const url = `${this.baseUrl}/delete-request/${requestId}`;
    return this.http.delete(url, { headers, responseType: 'text' as 'json' });
  }
  
  getNoOfPendingRequests(id:number):Observable<number>{

    const token = sessionStorage.getItem('token');
    if (!token) {
      throw new Error('No token found in sessionStorage');
    }
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.http.get<number>(`${this.baseUrl}/${id}/no-of-pending-requests`,{headers});

  }
  
}
