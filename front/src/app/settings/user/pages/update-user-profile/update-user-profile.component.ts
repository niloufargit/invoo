import {Component, inject, Input, Output, EventEmitter, OnChanges, SimpleChanges, OnInit} from '@angular/core';

import {UpdateUserFormComponent} from './update-user-form/update-user-form/update-user-form.component';
import {ActivatedRoute} from '@angular/router';


@Component({
  selector: 'app-update-user-profile',
  imports: [UpdateUserFormComponent],
  templateUrl: './update-user-profile.component.html',
  styleUrl: './update-user-profile.component.css',
})
export class UpdateUserProfileComponent implements OnInit{
  private route = inject(ActivatedRoute);

  //userId: string | null = null;
  userId?: string;

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.userId = params['id'] || null;
    });
  }
}
