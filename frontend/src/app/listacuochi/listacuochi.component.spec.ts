import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListacuochiComponent } from './listacuochi.component';

describe('ListacuochiComponent', () => {
  let component: ListacuochiComponent;
  let fixture: ComponentFixture<ListacuochiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListacuochiComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ListacuochiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
