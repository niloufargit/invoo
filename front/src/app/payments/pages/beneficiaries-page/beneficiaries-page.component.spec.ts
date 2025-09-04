import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BeneficiariesPage } from './beneficiaries-page.component';

describe('BeneficiariesPageComponent', () => {
  let component: BeneficiariesPage;
  let fixture: ComponentFixture<BeneficiariesPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BeneficiariesPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BeneficiariesPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
