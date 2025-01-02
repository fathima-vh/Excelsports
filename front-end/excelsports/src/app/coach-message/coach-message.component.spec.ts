import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CoachMessageComponent } from './coach-message.component';

describe('CoachMessageComponent', () => {
  let component: CoachMessageComponent;
  let fixture: ComponentFixture<CoachMessageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CoachMessageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CoachMessageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
