import { CommonModule } from '@angular/common';
import { Token } from '@angular/compiler';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../auth.service';
import { FooterComponent } from '../footer/footer.component';
import { HeaderComponent } from '../header/header.component';

@Component({
  selector: 'app-login',
  imports:[CommonModule,FormsModule,FooterComponent,RouterLink],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  constructor(private authService: AuthService, private router: Router) {}

  loginData = {
    email: '',
    password: '',
    role: '',
  };

  onSubmit() {
    if (this.loginData.role === 'COACH') {
      this.authService.loginCoach(this.loginData).subscribe(
        (response : any) => {
          console.log('Coach Login details:', response);
          sessionStorage.setItem('token', response.jwt);
          console.log(sessionStorage.getItem('token'));
          sessionStorage.setItem('email', response.email);
          sessionStorage.setItem('role', 'COACH'); 
          // alert('Coach Login Successful!');
          this.router.navigate(['/coach-home']);
          
          return;
        },
        (error) => {
          console.error('Login Failed:', error);
          alert('Coach Login Failed! Please try again.');
        }
      );
    } else {
      this.authService.loginAthlete(this.loginData).subscribe(
        (response :any) => {
          console.log('Athlete Login details:', response);
          
          console.log("token get : ",response.jwt);
          sessionStorage.setItem('token', response.jwt);
          console.log("token stored : ",sessionStorage.getItem('token'));
          sessionStorage.setItem('email', response.email);
          sessionStorage.setItem('role', 'ATHLETE'); 
          
          // alert('Athlete Login Successful!');
          this.router.navigate(['/athlete-home']);
          return;
        },
        (error) => {
          console.error('Login Failed:', error);
          alert('Athlete Login Failed! Please try again.');
        }
      );
    }
  }
}
