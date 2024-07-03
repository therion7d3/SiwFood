import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NuovaricettaComponent } from './nuovaricetta.component';

describe('NuovaricettaComponent', () => {
  let component: NuovaricettaComponent;
  let fixture: ComponentFixture<NuovaricettaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NuovaricettaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NuovaricettaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
