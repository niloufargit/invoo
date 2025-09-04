import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LandingCardSectionFeaturesComponent } from './landing-card-section-features.component';

describe('LandingCardSectionFeaturesComponent', () => {
  let component: LandingCardSectionFeaturesComponent;
  let fixture: ComponentFixture<LandingCardSectionFeaturesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LandingCardSectionFeaturesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LandingCardSectionFeaturesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
