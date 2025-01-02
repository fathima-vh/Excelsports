import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Coach } from '../model/Coach';
import { Login } from '../model/Login';
import { LoginComponent } from './login/login.component';
import { LoginResponse } from '../model/LoginResponse';
import { Athlete } from '../model/Athlete';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private baseUrl = 'http://localhost:8888/api/v1/'; 

  getUserRole(): string | null {
    return sessionStorage.getItem('role');
  }

  // Method to get the JWT token from sessionStorage
  getToken(): string | null {
    return sessionStorage.getItem('token');
  }

  logout() {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('role');
    this.router.navigate(['/login']);  // Redirect to login page
  }

  sAuthenticated(): boolean {
    return !!sessionStorage.getItem('jwtToken');
  }

  constructor(private http: HttpClient, private router:Router) {}

 
  signUpCoach(coach: Coach): Observable<Coach> {
    return this.http.post<Coach>(`${this.baseUrl}coach/register`, coach,{ withCredentials: true });
  }
  signUpAthlete(athlete: Athlete): Observable<Athlete> {
    return this.http.post<Athlete>(`${this.baseUrl}athlete/register`, athlete, {withCredentials: true,});
  }
  

  // Login Method
  loginCoach(data: Login): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.baseUrl}coach/login`, data,{ withCredentials: true });
  }

  loginAthlete(data: Login): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.baseUrl}athlete/login`, data,{ withCredentials: true });
  }


}
