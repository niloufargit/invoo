import {Component, OnInit} from '@angular/core';
import {NavBarLandingPageComponent} from './components/nav-bar-landing-page/nav-bar-landing-page.component';
import {
  LandingSectionHeaderComponent,
} from './components/landing-section-header/landing-section-header.component';
import {
  LandingSectionFeaturesComponent
} from './components/landing-section-features/landing-section-features.component';
import {
  LandingSectionObjectivesComponent
} from './components/landing-section-objectives/landing-section-objectives.component';
import {FooterLandingPageComponent} from './components/footer-landing-page/footer-landing-page.component';

@Component({
    selector: 'app-landing-page',
  imports: [
    NavBarLandingPageComponent,
    LandingSectionHeaderComponent,
    LandingSectionFeaturesComponent,
    LandingSectionObjectivesComponent,
    FooterLandingPageComponent,
  ],
    templateUrl: './landing-page.component.html',
    styleUrl: './landing-page.component.css'
})
export class LandingPageComponent implements OnInit {

  ngOnInit(): void {
    if (typeof window !== 'undefined') {
      window.addEventListener('scroll', this.onScroll);
    }
  }

  onScroll = (): void => {
    const navbar = document.getElementById('navbar-box');
    if (window.scrollY > 50) {
      navbar?.classList.add('navbar-scrolled');
    } else {
      navbar?.classList.remove('navbar-scrolled');
    }
  }


  scrollToSection() {
    const element = document.querySelector('.bouncing-animation');
    if (element) {
      element.classList.add('stopped');
    }    const target = document.getElementById('section2');
    if (target) {
      target.scrollIntoView({ behavior: 'smooth' });
    }
  }
}
