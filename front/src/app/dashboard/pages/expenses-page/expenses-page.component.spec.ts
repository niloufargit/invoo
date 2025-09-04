import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExpensesPage } from './expenses-page.component';

describe('ExpensesPageComponent', () => {
  let component: ExpensesPage;
  let fixture: ComponentFixture<ExpensesPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ExpensesPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ExpensesPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
