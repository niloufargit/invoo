import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenerateInvoicePageHeaderComponent } from './generate-invoice-page-header.component';

describe('GenerateInvoicePageHeaderComponent', () => {
  let component: GenerateInvoicePageHeaderComponent;
  let fixture: ComponentFixture<GenerateInvoicePageHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GenerateInvoicePageHeaderComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GenerateInvoicePageHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
