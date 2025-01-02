import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AthleteViewCoachComponent } from './athlete-view-coach.component';

describe('AthleteViewCoachComponent', () => {
  let component: AthleteViewCoachComponent;
  let fixture: ComponentFixture<AthleteViewCoachComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AthleteViewCoachComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AthleteViewCoachComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
