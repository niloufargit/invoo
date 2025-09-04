import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BeneficiaryContentBodyComponent } from './beneficiary-content-body.component';

describe('BeneficiaryContentBodyComponent', () => {
  let component: BeneficiaryContentBodyComponent;
  let fixture: ComponentFixture<BeneficiaryContentBodyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BeneficiaryContentBodyComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BeneficiaryContentBodyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
