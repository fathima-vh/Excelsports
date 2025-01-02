import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CoachViewAthletesComponent } from './coach-view-athletes.component';

describe('CoachViewAthletesComponent', () => {
  let component: CoachViewAthletesComponent;
  let fixture: ComponentFixture<CoachViewAthletesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CoachViewAthletesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CoachViewAthletesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
