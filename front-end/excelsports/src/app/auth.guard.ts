import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const token = sessionStorage.getItem('token');
    const role = sessionStorage.getItem('role');
  
    if (!token) {
      this.router.navigate(['/login']);
      return false;
    }
  
   
    if (role === next.data['role']) {
      return true;  
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }
  
}
