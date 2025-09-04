import {Component, effect, inject, OnInit} from '@angular/core';
import {AppStore} from '../../../shared/store/app.store';
import {Catalog, GetAllCatalogs} from '../../models/catalog.model';
import {CatalogCardComponent} from './components/catalog-card/catalog-card.component';
import {CatalogContentHeaderComponent} from './components/catalog-content-header/catalog-content-header.component';
import {ProductsTableComponent} from './components/products-table/products-table.component';

@Component({
  selector: 'app-catalogs-page',
  imports: [
    CatalogCardComponent,
    CatalogContentHeaderComponent,
    ProductsTableComponent
  ],
  templateUrl: './catalogs-page.component.html',
  styleUrl: './catalogs-page.component.css'
})
export class CatalogsPageComponent implements OnInit {

  protected catalogs: GetAllCatalogs;
  protected selectedCatalog: Catalog;
  protected store = inject(AppStore);


  ngOnInit() {
    this.store.closedModal();
    this.catalogs = this.store.catalogs();
    this.selectedCatalog = this.store.selectedCatalog() as Catalog;
    if(this.selectedCatalog){
      if(Object.keys(this.selectedCatalog).length === 0 && this.catalogs) {
        this.store.selectCatalog(this.catalogs[0]);
      }
      this.store.getProductsByCatalogId(this.selectedCatalog.id.toString());
    }
  }
}
