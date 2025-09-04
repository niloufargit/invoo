import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CatalogsDisplayComponent } from './catalogs-display.component';

describe('CatalogsDisplayComponent', () => {
  let component: CatalogsDisplayComponent;
  let fixture: ComponentFixture<CatalogsDisplayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CatalogsDisplayComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CatalogsDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
