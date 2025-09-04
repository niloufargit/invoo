import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BeneficiaryContentComponent } from './beneficiary-content.component';

describe('BeneficiaryContentComponent', () => {
  let component: BeneficiaryContentComponent;
  let fixture: ComponentFixture<BeneficiaryContentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BeneficiaryContentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BeneficiaryContentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
