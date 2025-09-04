import {Component, inject, Input, OnInit} from '@angular/core';
import {AppStore} from '../../store/app.store';

@Component({
  selector: 'app-page-template',
  imports: [],
  templateUrl: './page-template.component.html',
  standalone: true,
  styleUrl: './page-template.component.css'
})
export class PageTemplateComponent {

  @Input() title: string = '';
  @Input() subtitle?: string;
  protected store = inject(AppStore);

}
