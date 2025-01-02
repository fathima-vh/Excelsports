import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CoachHeaderComponent } from './coach-header.component';

describe('CoachHeaderComponent', () => {
  let component: CoachHeaderComponent;
  let fixture: ComponentFixture<CoachHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CoachHeaderComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CoachHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
