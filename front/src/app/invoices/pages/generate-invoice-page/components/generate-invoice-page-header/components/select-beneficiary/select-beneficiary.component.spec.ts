import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectBeneficiaryComponent } from './select-beneficiary.component';

describe('SelectBeneficiaryComponent', () => {
  let component: SelectBeneficiaryComponent;
  let fixture: ComponentFixture<SelectBeneficiaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SelectBeneficiaryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SelectBeneficiaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
