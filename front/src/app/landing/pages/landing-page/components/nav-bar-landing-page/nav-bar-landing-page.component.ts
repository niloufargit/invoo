import {Component, inject} from '@angular/core';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-nav-bar-landing-page',
  standalone: true, // Mark as standalone
  imports: [RouterModule],
  templateUrl: './nav-bar-landing-page.component.html',
  styleUrl: './nav-bar-landing-page.component.css'
})
export class NavBarLandingPageComponent {

  private router = inject(Router);

  async goToLoginPage() {
    await this.router.navigate(['/login']);
  }
}
