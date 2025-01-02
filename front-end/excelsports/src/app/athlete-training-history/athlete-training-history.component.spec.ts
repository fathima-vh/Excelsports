import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AthleteTrainingHistoryComponent } from './athlete-training-history.component';

describe('AthleteTrainingHistoryComponent', () => {
  let component: AthleteTrainingHistoryComponent;
  let fixture: ComponentFixture<AthleteTrainingHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AthleteTrainingHistoryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AthleteTrainingHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
