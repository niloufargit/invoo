import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-landing-card-section-features',
  templateUrl: './landing-card-section-features.component.html',
  styleUrls: ['./landing-card-section-features.component.css']
})
export class LandingCardComponent {
  @Input() title: string = '';
  @Input() description: string = '';
}
