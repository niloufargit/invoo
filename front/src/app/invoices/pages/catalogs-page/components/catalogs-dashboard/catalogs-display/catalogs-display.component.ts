import {Component, inject} from '@angular/core';
import {Router, RouterOutlet} from "@angular/router";
import {AppStore} from '../../../../../../shared/store/app.store';
import {NgIf} from '@angular/common';
import {CatalogWithCategories} from '../../../../../models/catalog.model';

@Component({
  selector: 'app-catalogs-display',
  imports: [],
  templateUrl: './catalogs-display.component.html',
  styleUrl: './catalogs-display.component.css'
})
export class CatalogsDisplayComponent {

  store = inject(AppStore);
  router = inject(Router);
  hoveredCatalog: number | null = null;

  showCatalog(cat: CatalogWithCategories) {
    this.store.selectCatalog(cat);
    this.router.navigate(['/catalogs/display']);
    this.hoveredCatalog = null;
  }

  addNewCatalog() {
    this.router.navigate(['/catalogs/add']);
  }
}
