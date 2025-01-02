import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TrainingHistoryChartComponent } from './training-history-chart.component';

describe('TrainingHistoryChartComponent', () => {
  let component: TrainingHistoryChartComponent;
  let fixture: ComponentFixture<TrainingHistoryChartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TrainingHistoryChartComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TrainingHistoryChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
