import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenerateInvoicePage } from './generate-invoice-page.component';

describe('GenerateInvoicePageComponent', () => {
  let component: GenerateInvoicePage;
  let fixture: ComponentFixture<GenerateInvoicePage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GenerateInvoicePage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GenerateInvoicePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
