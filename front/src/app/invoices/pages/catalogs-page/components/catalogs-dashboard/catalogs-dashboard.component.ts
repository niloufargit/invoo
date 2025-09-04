import {Component, effect, inject, OnInit} from '@angular/core';
import {PageTemplateComponent} from '../../../../../shared/components/page-template/page-template.component';
import {Router, RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import {Catalog, GetAllCatalogs} from '../../../../models/catalog.model';
import {AppStore} from '../../../../../shared/store/app.store';
import {CatalogsDisplayComponent} from './catalogs-display/catalogs-display.component';

@Component({
  selector: 'app-catalogs-dashboard',
  imports: [
    PageTemplateComponent,
    RouterLink,
    RouterLinkActive,
    RouterOutlet,
    CatalogsDisplayComponent,
  ],
  templateUrl: './catalogs-dashboard.component.html',
  styleUrl: './catalogs-dashboard.component.css'
})
export class CatalogsDashboardComponent implements OnInit {

  protected catalogs: GetAllCatalogs;
  protected selectedCatalog: Catalog;
  protected store = inject(AppStore);
  router = inject(Router);

 constructor() {
    effect(() => {
      if (!this.store.selectedCompany()?.id) {
        this.router.navigate(['/']);
      }
    });
 }

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
