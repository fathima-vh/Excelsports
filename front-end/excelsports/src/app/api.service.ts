import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  private apiUrl = 'https://sportapi7.p.rapidapi.com/api/v1/sport/football/events/live';
  private headers = {
    'x-rapidapi-key': 'b1f9bd1b2fmsheb95313b9f635d2p1b2ecbjsn1678d8b18133',
    'x-rapidapi-host': 'sportapi7.p.rapidapi.com',
  };

  constructor(private http: HttpClient) {}

  getLiveEvents(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl, { headers: this.headers });
  }
}
