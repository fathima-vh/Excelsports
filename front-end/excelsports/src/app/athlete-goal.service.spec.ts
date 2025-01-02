import { TestBed } from '@angular/core/testing';

import { AthleteGoalService } from './athlete-goal.service';

describe('AthleteGoalService', () => {
  let service: AthleteGoalService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AthleteGoalService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
