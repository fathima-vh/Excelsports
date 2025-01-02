import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AthleteViewTrainingSessionsComponent } from './athlete-view-training-sessions.component';

describe('AthleteViewTrainingSessionsComponent', () => {
  let component: AthleteViewTrainingSessionsComponent;
  let fixture: ComponentFixture<AthleteViewTrainingSessionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AthleteViewTrainingSessionsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AthleteViewTrainingSessionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
