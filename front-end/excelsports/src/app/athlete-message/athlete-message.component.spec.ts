import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AthleteMessageComponent } from './athlete-message.component';

describe('AthleteMessageComponent', () => {
  let component: AthleteMessageComponent;
  let fixture: ComponentFixture<AthleteMessageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AthleteMessageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AthleteMessageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
