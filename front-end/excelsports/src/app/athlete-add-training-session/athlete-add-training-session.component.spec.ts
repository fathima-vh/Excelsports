import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AthleteAddTrainingSessionComponent } from './athlete-add-training-session.component';

describe('AthleteAddTrainingSessionComponent', () => {
  let component: AthleteAddTrainingSessionComponent;
  let fixture: ComponentFixture<AthleteAddTrainingSessionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AthleteAddTrainingSessionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AthleteAddTrainingSessionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
