import {Component, effect, inject, OnDestroy, OnInit, signal} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {Router, RouterLink} from '@angular/router';

@Component({
  selector: 'app-account-validation-page',
  templateUrl: './account-validation-page.component.html',
  imports: [
    RouterLink
  ],
  styleUrls: ['./account-validation-page.component.css']
})
export class AccountValidationPage implements OnInit, OnDestroy{
  stateValidateAccount = signal<'loading' | 'success' | 'error' | 'timeout'>('loading');
  private router = inject(Router);
  protected authService = inject(AuthService)
  private timerId: any;

  ngOnInit(): void {
    this.pollAccountValidation();
  }

  pollAccountValidation(): void {
    const maxDuration = 60000; // 1 minute
    const intervalDuration = 3000; // 3 seconds
    let elapsedTime = 0;

    const intervalId = setInterval(async () => {
      try {
        const isValidated = await this.authService.checkIfAccountIsValidated();
        if (isValidated) {
          this.stateValidateAccount.set('success');
          clearInterval(intervalId);
          this.timerId = setTimeout(() => {
            this.router.navigate(['/']);
          }, 2000);
        }
      } catch (error) {
        this.stateValidateAccount.set('error');
        clearInterval(intervalId);
      }

      elapsedTime += intervalDuration;
      if (elapsedTime >= maxDuration) {
        clearInterval(intervalId);
        if (this.stateValidateAccount() === 'loading') {
          this.stateValidateAccount.set('error');
        }
      }
    }, intervalDuration);
  }
  retry(): void {
    this.stateValidateAccount.set('loading');
    this.pollAccountValidation();
  }
  ngOnDestroy(): void {
    if (this.timerId) {
      clearTimeout(this.timerId);
    }
  }


}
