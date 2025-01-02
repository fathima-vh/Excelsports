import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { Athlete } from '../../model/Athlete';
import { AuthService } from '../auth.service';
import { FooterComponent } from '../footer/footer.component';

@Component({
  selector: 'app-athlete-signup',
  standalone: true,
  imports: [CommonModule, FormsModule,FooterComponent,RouterLink],
  templateUrl: './athlete-signup.component.html',
  styleUrl:'./athlete-signup.component.css'
})
export class AthleteSignupComponent {
  constructor(private authService: AuthService, private router: Router) {}

  signupData: Athlete = {
    id:0,
    name: '',
    email: '',
    password: '',
    dob: '',
    sport: '',
    age: 0,
    weight: 0,
    height: 0,
    goalCalorie:0
  };

  onSubmit() {
    this.authService.signUpAthlete(this.signupData).subscribe(
      (response) => {
        console.log('Athlete Signup details:', response);
        
        this.router.navigate(['/login']);
      },
      (error) => {
        console.error('Signup Failed:', error);
        alert('Athlete Signup Failed! Please try again.');
      }
    );
  }
}
