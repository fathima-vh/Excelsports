import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AthleteHeaderComponent } from './athlete-header.component';

describe('AthleteHeaderComponent', () => {
  let component: AthleteHeaderComponent;
  let fixture: ComponentFixture<AthleteHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AthleteHeaderComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AthleteHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
