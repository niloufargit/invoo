import {
  ActivatedRouteSnapshot,
  CanActivate,
  GuardResult,
  MaybeAsync, Router,
  RouterStateSnapshot
} from '@angular/router';
import {AppStore} from '../../../../shared/store/app.store';
import {inject, Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SelectedCompanyGuard implements CanActivate {

  private store = inject(AppStore)
  private router = inject(Router)

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
    if(!this.store.isSelectedCompany()) {
      this.router.navigate(['/']);
      return false;
    }
    return true;
  }

}
