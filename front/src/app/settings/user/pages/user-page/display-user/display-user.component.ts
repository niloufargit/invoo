import {Component, inject, OnInit} from '@angular/core';
import {AppStore} from '../../../../../shared/store/app.store';
import {Router} from '@angular/router';

@Component({
  selector: 'app-display-user',
  imports: [],
  templateUrl: './display-user.component.html',
  styleUrl: './display-user.component.css'
})
export class DisplayUserComponent {


  store = inject(AppStore);
  private router = inject(Router);


  currentUser: any;
  ngOnInit(): void {
    this.currentUser = this.store.user();
  }



  editUser(): void {
    if (!this.currentUser?.id) {
      console.error('No user id found!');
      return;
    }
    this.router.navigate(['/settings/user/edit', this.currentUser.id]);
  }
}
