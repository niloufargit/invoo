import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CatalogsPageComponent } from './catalogs-page.component';

describe('CatalogsPageComponent', () => {
  let component: CatalogsPageComponent;
  let fixture: ComponentFixture<CatalogsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CatalogsPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CatalogsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
