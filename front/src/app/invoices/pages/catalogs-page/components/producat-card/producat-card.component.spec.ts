import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProducatCardComponent } from './producat-card.component';

describe('ProducatCardComponent', () => {
  let component: ProducatCardComponent;
  let fixture: ComponentFixture<ProducatCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProducatCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProducatCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
