import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RevenuesPage } from './revenues-page.component';

describe('RevenuesPageComponent', () => {
  let component: RevenuesPage;
  let fixture: ComponentFixture<RevenuesPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RevenuesPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RevenuesPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
