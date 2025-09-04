import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionsContentHeaderComponent } from './transactions-content-header.component';

describe('TransactionsContentHeaderComponent', () => {
  let component: TransactionsContentHeaderComponent;
  let fixture: ComponentFixture<TransactionsContentHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransactionsContentHeaderComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransactionsContentHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
