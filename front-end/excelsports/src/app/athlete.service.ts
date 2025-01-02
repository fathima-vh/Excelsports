import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { Athlete } from '../model/Athlete';
import { Coach } from '../model/Coach';

@Injectable({
  providedIn: 'root'
})
export class AthleteService {

   private baseUrl = 'http://localhost:8888/api/v1/athletes'; // Adjust the URL

  constructor(private http: HttpClient) {}
  private athleteId!: number; 

  setAthleteId(id: number): void {
    this.athleteId = id;
  }

  getAthleteId(): number {
    return this.athleteId;
  }

  getAthleteDetails(email: string): Observable<Athlete> {
    const token = sessionStorage.getItem('token');  // Get token from sessionStorage
    if (!token) {
      throw new Error('No token found in sessionStorage');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<Athlete>(`${this.baseUrl}/${email}`, { headers });
  }

  getCoachForAthlete(athleteId: number): Observable<Coach> {
    const token = sessionStorage.getItem('token'); 
    if (!token) {
      throw new Error('No token found in sessionStorage');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<Coach>(`${this.baseUrl}/coach/${athleteId}`, { headers });
  }

  updateAthleteDetails(athlete :Athlete): Observable<Athlete> {
    const token = sessionStorage.getItem('token');  
    if (!token) {
      throw new Error('No token found in sessionStorage');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.put<Athlete>(`${this.baseUrl}/${athlete.id}`, athlete,{ headers });
  }

  getAllCoaches(): Observable<Coach[]> {
    const token = sessionStorage.getItem('token');  

    if (!token) {
      throw new Error('No token found in sessionStorage');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<Coach[]>(`${this.baseUrl}/coaches`, { headers });
  }

  requestCoach(coachId: number): Observable<any> {
  
    const token = sessionStorage.getItem('token');
    const id = Number(sessionStorage.getItem('athleteId'));
    if (!token) {
      throw new Error('No token found in sessionStorage');
    }
    
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
  
    console.log("athlete id : ",this.athleteId,"requestCoach - token: ", token);
  
    return this.http.post<any>(`${this.baseUrl}/${id}/request-coach/${coachId}`, {}, { headers, responseType: 'text' as 'json' } ).pipe(
      catchError((error) => {
        let errorMessage = 'An unknown error occurred.';

        if (error.status === 400) {
          // Handle bad request, e.g., athlete has a pending request
          errorMessage = error.error || 'You already have a pending request with another coach.';
        } else if (error.status === 404) {
          // Handle not found, e.g., coach not found
          errorMessage = 'Coach not found. Please try again.';
        } else if (error.status === 500) {
          // Handle server errors
          errorMessage = 'Failed to send request. Please try again later.';
        }

        return throwError(errorMessage);  // Propagate error message
      })
    );
  }
  deleteCoachRequest(coachId: number): Observable<any> {
    const token = sessionStorage.getItem('token');
    const athleteId = this.athleteId;

    if (!token) {
      throw new Error('No token found in sessionStorage');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.http.delete<any>(`${this.baseUrl}/${athleteId}/delete-request/${coachId}`, { headers })
      .pipe(
        catchError((error) => {
          let errorMessage = 'An unknown error occurred.';

          if (error.status === 400) {
            errorMessage = 'You can only delete pending requests.';
          } else if (error.status === 404) {
            errorMessage = 'Request not found.';
          }

          return throwError(errorMessage);  // Propagate error message
        })
      );
  }

  private url = 'https://inspirational-quote-generator.p.rapidapi.com/quoteGenerator';

  getQuote(): Observable<any> {
    const headers = new HttpHeaders({
      'x-rapidapi-key': '6a70a9e56cmsh8ba731c614500a6p1d94bcjsncda051bc1f97',
      'x-rapidapi-host': 'inspirational-quote-generator.p.rapidapi.com'
    });
    // const headers = new HttpHeaders({
    //   'x-rapidapi-key': 'b1f9bd1b2fmsheb95313b9f635d2p1b2ecbjsn1678d8b18133',
    //   'x-rapidapi-host': 'inspirational-quote-generator.p.rapidapi.com'
    // });

    return this.http.get<any>(this.url, { headers });
  }
}
