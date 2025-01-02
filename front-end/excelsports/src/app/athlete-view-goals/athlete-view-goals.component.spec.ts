import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AthleteViewGoalsComponent } from './athlete-view-goals.component';

describe('AthleteViewGoalsComponent', () => {
  let component: AthleteViewGoalsComponent;
  let fixture: ComponentFixture<AthleteViewGoalsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AthleteViewGoalsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AthleteViewGoalsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
