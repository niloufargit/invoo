import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TermsConditionsPage } from './terms-conditions-page.component';

describe('TermsConditionsPageComponent', () => {
  let component: TermsConditionsPage;
  let fixture: ComponentFixture<TermsConditionsPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TermsConditionsPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TermsConditionsPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
