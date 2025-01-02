
import { AthleteHomeComponent } from './athlete-home/athlete-home.component';
import { CoachHomeComponent } from './coach-home/coach-home.component';
import { CoachSignupComponent } from './coach-signup/coach-signup.component';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './auth.guard'; // Import the guard
import { AthleteSignupComponent } from './athlete-signup/athlete-signup.component';
import { HomeComponent } from './home/home.component';
import { AthleteViewCoachComponent } from './athlete-view-coach/athlete-view-coach.component';
import { AthleteAddTrainingSessionComponent } from './athlete-add-training-session/athlete-add-training-session.component';
import { Routes } from '@angular/router';
import { CoachViewAthletesComponent } from './coach-view-athletes/coach-view-athletes.component';
import { CoachHeaderComponent } from './coach-header/coach-header.component';
import { CoachViewReqComponent } from './coach-view-req/coach-view-req.component';
import { CoachSetGoalsComponent } from './coach-set-goals/coach-set-goals.component';
import { AthleteProfileComponent } from './athlete-profile/athlete-profile.component';
import { AthleteEditTrainingSessionComponent } from './athlete-edit-training-session/athlete-edit-training-session.component';
import { AthleteViewTrainingSessionsComponent } from './athlete-view-training-sessions/athlete-view-training-sessions.component';
import { AthleteMessageComponent } from './athlete-message/athlete-message.component';
import { AthleteTrainingHistoryComponent } from './athlete-training-history/athlete-training-history.component';
import { CoachMessageComponent } from './coach-message/coach-message.component';
import { CoachViewHistoryComponent } from './coach-view-history/coach-view-history.component';
import { CalenderComponent } from './calender/calender.component';
import { AthleteViewGoalsComponent } from './athlete-view-goals/athlete-view-goals.component';
import { CoachProfileComponent } from './coach-profile/coach-profile.component';
// import { CoachAddGoalComponent } from '../coach-add-goal/coach-add-goal.component';
// import { CoachViewHistoryComponent } from '../coach-view-history/coach-view-history.component';

export const routes: Routes = [
    {path:'home',component:HomeComponent},
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'coach-signup', component: CoachSignupComponent },
  { path: 'athlete-signup', component: AthleteSignupComponent },
  { path: 'login', component: LoginComponent },
  {
    path: 'coach-home',
    component: CoachHomeComponent,
    canActivate: [AuthGuard], 
    data: { role: 'COACH' },  
  },
  {
    path: 'athlete-home',
    component: AthleteHomeComponent,
    canActivate: [AuthGuard], 
    data: { role: 'ATHLETE' },
  },
  {
    path:'athlete-view-coach/:athleteId',
    component:AthleteViewCoachComponent,
    canActivate:[AuthGuard],
    data:{role:'ATHLETE'}
  },
  {
    path:'athlete-add-training-session',
    component:AthleteAddTrainingSessionComponent,
    canActivate:[AuthGuard],
    data:{role:'ATHLETE'}
  },
  {
    path:'athlete-edit-training-session/:id',
    component:AthleteEditTrainingSessionComponent,
    canActivate:[AuthGuard],
    data:{role:'ATHLETE'}
  },
  {
    path:'athlete-calender',
    component:CalenderComponent,
    canActivate:[AuthGuard],
    data:{role:'ATHLETE'}
  },
  {
    path:'athlete-message',
    component:AthleteMessageComponent,
    canActivate:[AuthGuard],
    data:{role:'ATHLETE'}
  },
  {
    path:'athlete-goals',
    component:AthleteViewGoalsComponent,
    canActivate:[AuthGuard],
    data:{role:'ATHLETE'}
  },
  {
    path:'athlete-training-history/:id',
    component:AthleteTrainingHistoryComponent,
    canActivate:[AuthGuard],
    data:{role:'ATHLETE'}
  },
  {
    path:'athlete-view-sessions/:id',
    component:AthleteViewTrainingSessionsComponent,
    canActivate:[AuthGuard],
    data:{role:'ATHLETE'}
  },
  {
    path:'athlete-view-profile',
    component:AthleteProfileComponent,
    canActivate:[AuthGuard],
    data:{role:'ATHLETE'}
  },
   { path: 'coach-athletes',
    component: CoachViewAthletesComponent,
    canActivate: [AuthGuard],
    data: {role: 'COACH'},
  },
  {
    path: 'coach-view-requests',
    component:CoachViewReqComponent,
    canActivate:[AuthGuard],
    data:{role:'COACH'}
  },
  {
    path: 'coach-set-goals/:athleteId',
    component:CoachSetGoalsComponent,
    canActivate:[AuthGuard],
    data:{role:'COACH'}
  },
  {
    path: 'coach-view-goals',
    component:CoachSetGoalsComponent,
    canActivate:[AuthGuard],
    data:{role:'COACH'}
  },
  {
    path: 'coach-view-athletes',
    component:CoachViewAthletesComponent,
    canActivate:[AuthGuard],
    data:{role:'COACH'}
  },
  {
    path: 'coach-message/:athleteID',
    component:CoachMessageComponent,
    canActivate:[AuthGuard],
    data:{role:'COACH'}
  },
  {
    path: 'coach-view-history/:athleteId',
    component:CoachViewHistoryComponent,
    canActivate:[AuthGuard],
    data:{role:'COACH'}
  },
  {
    path: 'coach-profile',
    component:CoachProfileComponent,
    canActivate:[AuthGuard],
    data:{role:'COACH'}
  }
];
