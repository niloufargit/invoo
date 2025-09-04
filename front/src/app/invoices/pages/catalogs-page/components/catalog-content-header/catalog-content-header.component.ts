import {Component, inject} from '@angular/core';
import {AppStore} from '../../../../../shared/store/app.store';
import {CatalogWithCategories} from '../../../../models/catalog.model';

@Component({
  selector: 'app-catalog-content-header',
  imports: [],
  standalone: true,
  templateUrl: './catalog-content-header.component.html',
  styleUrl: './catalog-content-header.component.css'
})
export class CatalogContentHeaderComponent {


  store = inject(AppStore)
  protected readonly NaN = NaN;

  changeCatalogs($event: Event) {
    const target = $event.target as HTMLSelectElement;
    const idSelectedCatalog = Number(target.value) ;

    const selectedCatalog =
      this.store.catalogs()?.find((catalog: CatalogWithCategories) => catalog.id === idSelectedCatalog);
    if (selectedCatalog) {
      this.store.selectCatalog(selectedCatalog);
      this.store.getProductsByCatalogId(selectedCatalog.id.toString());
      this.store.unselectCategory();
    }
  }

  categoryProducts($event: Event) {
    const target = $event.target as HTMLSelectElement;
    const idSelectedCategory = Number(target.value) ;
    this.store.selectCategory(idSelectedCategory);
  }
}
