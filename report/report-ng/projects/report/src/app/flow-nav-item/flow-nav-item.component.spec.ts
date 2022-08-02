import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FlowNavItemComponent } from './flow-nav-item.component';

describe('FlowNavItemComponent', () => {
  let component: FlowNavItemComponent;
  let fixture: ComponentFixture<FlowNavItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FlowNavItemComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FlowNavItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
