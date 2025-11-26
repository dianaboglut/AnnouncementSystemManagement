import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BillboardComponent } from './billboard.component';

describe('Billboard', () => {
  let component: BillboardComponent;
  let fixture: ComponentFixture<BillboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BillboardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BillboardComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
