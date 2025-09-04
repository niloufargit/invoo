import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddNewCatalogComponent } from './add-new-catalog.component';

describe('AddNewCatalogComponent', () => {
  let component: AddNewCatalogComponent;
  let fixture: ComponentFixture<AddNewCatalogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddNewCatalogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddNewCatalogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
