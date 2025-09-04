import {Component, Input} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {Product} from '../../../../models/product.model';

@Component({
    selector: 'app-producat-card',
    imports: [
        FormsModule
    ],
    templateUrl: './producat-card.component.html',
    standalone: true,
    styleUrl: './producat-card.component.css'
})
export class ProducatCardComponent {
  @Input() product: Product;
  @Input() editProduct!: (product: { price: number; name: string; description: string; category: string; selected: boolean }) => void;
  @Input() deleteProduct!: (product: { price: number; name: string; description: string; category: string; selected: boolean }) => void;
}
