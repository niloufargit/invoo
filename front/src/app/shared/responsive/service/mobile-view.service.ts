import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MobileViewService {
  isMobile(): boolean {
    return window.innerWidth < 768;
  }
}
