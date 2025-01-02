import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { Coach } from '../../model/Coach';
import { AuthService } from '../auth.service';
import { FooterComponent } from '../footer/footer.component';
import { HeaderComponent } from '../header/header.component';

@Component({
  selector: 'app-coach-signup',
  imports: [CommonModule,FormsModule,FooterComponent,RouterLink],
  templateUrl: './coach-signup.component.html',
  styleUrl: './coach-signup.component.css'
})
export class CoachSignupComponent {
  coach: Coach = {
    id:0,
    name: '',
    email: '',
    password: '',
    sport: '',
    experience: 0,
    athleteCount:0
  };

  constructor(private authService: AuthService,private router:Router) {}

  onSubmit() {
    this.authService.signUpCoach(this.coach).subscribe(
      (response) => {
        console.log('Coach successfully signed up:', response);
        // alert('Sign-Up Successful!');
        this.router.navigate(['/login']);
      },
      (error) => {
        console.error('Sign-Up Failed:', error);
        if (error.status === 200) {
          alert('Sign-Up Successful, but something went wrong with response handling.');
        } else {
          alert('Sign-Up Failed! Please try again.');
        }
      }
    );
  }
}