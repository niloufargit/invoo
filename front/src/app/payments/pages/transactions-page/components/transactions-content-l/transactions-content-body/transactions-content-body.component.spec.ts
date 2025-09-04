import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionsContentBodyComponent } from './transactions-content-body.component';

describe('TransactionsContentBodyComponent', () => {
  let component: TransactionsContentBodyComponent;
  let fixture: ComponentFixture<TransactionsContentBodyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransactionsContentBodyComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransactionsContentBodyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
