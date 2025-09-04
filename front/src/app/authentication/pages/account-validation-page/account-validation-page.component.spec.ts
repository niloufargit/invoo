import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountValidationPage } from './account-validation-page.component';

describe('AccountValidationPageComponent', () => {
  let component: AccountValidationPage;
  let fixture: ComponentFixture<AccountValidationPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AccountValidationPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AccountValidationPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
