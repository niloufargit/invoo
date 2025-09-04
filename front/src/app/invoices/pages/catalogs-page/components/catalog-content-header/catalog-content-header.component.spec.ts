import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CatalogContentHeaderComponent } from './catalog-content-header.component';

describe('CatalogContentHeaderComponent', () => {
  let component: CatalogContentHeaderComponent;
  let fixture: ComponentFixture<CatalogContentHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CatalogContentHeaderComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CatalogContentHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
