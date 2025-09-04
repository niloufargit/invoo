import {Component, inject} from '@angular/core';
import {CardComponentComponent} from '../../../../../shared/components/cards/card-component/card-component.component';
import {DropdownMenuComponent} from '../../../../../shared/components/drop-down-menu/drop-down-menu.component';
import {AppStore} from '../../../../../shared/store/app.store';

@Component({
    selector: 'app-catalog-card',
    imports: [
        CardComponentComponent,
    ],
    templateUrl: './catalog-card.component.html',
    standalone: true,
    styleUrl: './catalog-card.component.css'
})
export class CatalogCardComponent {

  protected store = inject(AppStore)

}
