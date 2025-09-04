import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TemporaryValidationPage } from './temporary-validation-page.component';

describe('TemporaryValidationPageComponent', () => {
  let component: TemporaryValidationPage;
  let fixture: ComponentFixture<TemporaryValidationPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TemporaryValidationPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TemporaryValidationPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
