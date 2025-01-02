import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  
  private baseUrl = 'http://localhost:8888/api/v1/messages';  


  constructor(private http: HttpClient) {}

  sendMessage(message:any): Observable<any> {
    const token = sessionStorage.getItem('token');
    if (!token) {
      throw new Error('No token found in sessionStorage');
    }
  
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`).set('Content-Type', 'application/json');
    // console.log(message);
    return this.http.post(`${this.baseUrl}/send`, message, { headers });
  }
  getMessagesForCoach(coachId: number): Observable<any[]> {
    const token = sessionStorage.getItem('token');  // Get token from sessionStorage
    
    if (!token) {
      throw new Error('No token found in sessionStorage');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any[]>(`${this.baseUrl}/coach-inbox?coachId=${coachId}`,{headers});
  }
  getMessagesOfCoachAndAthlete(athleteId:number,coachId:number):Observable<any[]>{
    const token = sessionStorage.getItem('token');  // Get token from sessionStorage
    
    if (!token) {
      throw new Error('No token found in sessionStorage');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    // Include athleteId and coachId as query parameters in the URL
  const url = `${this.baseUrl}/athlete-inbox?athleteId=${athleteId}&coachId=${coachId}`;
  
  return this.http.get<any[]>(url, { headers });
  }
  markMessageAsRead(messageId: number): Observable<void> {
    const token = sessionStorage.getItem('token');  // Get token from sessionStorage
  
    if (!token) {
      throw new Error('No token found in sessionStorage');
    }
  
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.put<void>(`${this.baseUrl}/mark-as-read/${messageId}`, {}, { headers }); // Pass headers in options
  }
  

  deleteMessage(messageId: number): Observable<void> {
    const token = sessionStorage.getItem('token');  // Get token from sessionStorage
    
    if (!token) {
      throw new Error('No token found in sessionStorage');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.delete<void>(`${this.baseUrl}/delete/${messageId}`,{headers});
  }

  getCountOfUnreadMessages(id:number): Observable<number>{

    const token = sessionStorage.getItem('token');  // Get token from sessionStorage
    
    if (!token) {
      throw new Error('No token found in sessionStorage');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.http.get<number>(`${this.baseUrl}/${id}/unread-count`,{headers});

  }
}
