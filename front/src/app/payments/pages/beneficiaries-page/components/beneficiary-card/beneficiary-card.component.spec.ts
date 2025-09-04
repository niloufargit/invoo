import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BeneficiaryCardComponent } from './beneficiary-card.component';

describe('BeneficiaryCardComponent', () => {
  let component: BeneficiaryCardComponent;
  let fixture: ComponentFixture<BeneficiaryCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BeneficiaryCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BeneficiaryCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
