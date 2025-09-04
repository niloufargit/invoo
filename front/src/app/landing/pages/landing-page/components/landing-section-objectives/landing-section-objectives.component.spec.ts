import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LandingSectionObjectivesComponent } from './landing-section-objectives.component';

describe('LandingSectionObjectivesComponent', () => {
  let component: LandingSectionObjectivesComponent;
  let fixture: ComponentFixture<LandingSectionObjectivesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LandingSectionObjectivesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LandingSectionObjectivesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
