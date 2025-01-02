import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AthleteHomeComponent } from './athlete-home.component';

describe('AthleteHomeComponent', () => {
  let component: AthleteHomeComponent;
  let fixture: ComponentFixture<AthleteHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AthleteHomeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AthleteHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
