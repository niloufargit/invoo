import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-temporary-validation-page',
  imports: [],
  templateUrl: './temporary-validation-page.component.html',
  styleUrl: './temporary-validation-page.component.css'
})
export class TemporaryValidationPage implements OnInit {

  private route = inject(ActivatedRoute);
  private authService = inject(AuthService);

  async ngOnInit(): Promise<void> {
    const token = this.route.snapshot.paramMap.get('token');
    if (!token) {
      console.error('Token not found in URL');
      return;
    }

    try {
      await this.authService.validateAccount(token);
    } catch (error) {
      console.error('Error validating account:', error);
    }

    const closeDelay = 100; // Configurable delay in milliseconds
    setTimeout(() => {
      window.close();
    }, closeDelay);
  }
}
