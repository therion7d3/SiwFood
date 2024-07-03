import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RicettadetailComponent } from './ricettadetail.component';

describe('RicettadetailComponent', () => {
  let component: RicettadetailComponent;
  let fixture: ComponentFixture<RicettadetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RicettadetailComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RicettadetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
