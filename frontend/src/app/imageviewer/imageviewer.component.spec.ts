import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImageViewComponent } from './imageviewer.component';

describe('ImageviewerComponent', () => {
  let component: ImageViewComponent;
  let fixture: ComponentFixture<ImageViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ImageViewComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ImageViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
