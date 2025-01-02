import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AthleteSignupComponent } from './athlete-signup.component';

describe('AthleteSignupComponent', () => {
  let component: AthleteSignupComponent;
  let fixture: ComponentFixture<AthleteSignupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AthleteSignupComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AthleteSignupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
