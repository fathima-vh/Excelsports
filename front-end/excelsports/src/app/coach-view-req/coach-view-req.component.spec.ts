import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CoachViewReqComponent } from './coach-view-req.component';

describe('CoachViewReqComponent', () => {
  let component: CoachViewReqComponent;
  let fixture: ComponentFixture<CoachViewReqComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CoachViewReqComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CoachViewReqComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
