import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenerateInvoicePageContentComponent } from './generate-invoice-page-content.component';

describe('GenerateInvoicePageContentComponent', () => {
  let component: GenerateInvoicePageContentComponent;
  let fixture: ComponentFixture<GenerateInvoicePageContentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GenerateInvoicePageContentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GenerateInvoicePageContentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
