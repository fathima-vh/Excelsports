import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CoachViewHistoryComponent } from './coach-view-history.component';

describe('CoachViewHistoryComponent', () => {
  let component: CoachViewHistoryComponent;
  let fixture: ComponentFixture<CoachViewHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CoachViewHistoryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CoachViewHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
