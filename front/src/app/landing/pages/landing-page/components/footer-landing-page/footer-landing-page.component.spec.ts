import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FooterLandingPageComponent } from './footer-landing-page.component';

describe('FooterLandingPageComponent', () => {
  let component: FooterLandingPageComponent;
  let fixture: ComponentFixture<FooterLandingPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FooterLandingPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FooterLandingPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
