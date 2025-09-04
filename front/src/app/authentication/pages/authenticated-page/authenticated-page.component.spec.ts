import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthenticatedPage } from './authenticated-page.component';

describe('AuthenticatedPageComponent', () => {
  let component: AuthenticatedPage;
  let fixture: ComponentFixture<AuthenticatedPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AuthenticatedPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AuthenticatedPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
