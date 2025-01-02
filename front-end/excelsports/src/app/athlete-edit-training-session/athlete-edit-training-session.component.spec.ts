import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AthleteEditTrainingSessionComponent } from './athlete-edit-training-session.component';

describe('AthleteEditTrainingSessionComponent', () => {
  let component: AthleteEditTrainingSessionComponent;
  let fixture: ComponentFixture<AthleteEditTrainingSessionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AthleteEditTrainingSessionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AthleteEditTrainingSessionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
