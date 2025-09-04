import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BeneficiaryContentHeaderComponent } from './beneficiary-content-header.component';

describe('BeneficiaryContentHeaderComponent', () => {
  let component: BeneficiaryContentHeaderComponent;
  let fixture: ComponentFixture<BeneficiaryContentHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BeneficiaryContentHeaderComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BeneficiaryContentHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
