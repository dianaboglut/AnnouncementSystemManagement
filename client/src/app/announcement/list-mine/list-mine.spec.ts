import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListMine } from './list-mine';

describe('ListMine', () => {
  let component: ListMine;
  let fixture: ComponentFixture<ListMine>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListMine]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListMine);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
