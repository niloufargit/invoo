import {Component, Input} from '@angular/core';
import {NgClass} from '@angular/common';

@Component({
  selector: 'app-card-component',
  imports: [
    NgClass
  ],
  templateUrl: './card-component.component.html',
  standalone: true,
  styleUrl: './card-component.component.css'
})
export class CardComponentComponent {

  @Input() customClass?: string;

}
