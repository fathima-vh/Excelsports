import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private baseUrl = 'http://localhost:8888/api/v1/category';

  constructor(private http: HttpClient) { }

  getAllCategories():Observable<string[]>{
    const token = sessionStorage.getItem('token');
    if (!token) {
      throw new Error('No token found in sessionStorage');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.http.get<string[]>(`${this.baseUrl}/category-names`, { headers });

  }

  getExercisesByCategory(categoryName:string): Observable<string[]> {
    const token = sessionStorage.getItem('token');
    if (!token) {
      throw new Error('No token found in sessionStorage');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<string[]>(`${this.baseUrl}/category/${categoryName}`, { headers });
  }

}
