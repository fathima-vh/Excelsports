import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CoachSetGoalsComponent } from './coach-set-goals.component';

describe('CoachSetGoalsComponent', () => {
  let component: CoachSetGoalsComponent;
  let fixture: ComponentFixture<CoachSetGoalsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CoachSetGoalsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CoachSetGoalsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
