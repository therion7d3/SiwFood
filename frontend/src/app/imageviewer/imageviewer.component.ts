import { Component } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { fotoService } from '../services/foto.service';
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-image-view',
  standalone: true,
  imports: [
    NgIf
  ],
  template: `
    <img [src]="imageUrl" *ngIf="imageUrl">
    <button (click)="onFetchImage()">Fetch Image</button>
  `
})
export class ImageViewComponent {
  imageUrl: any;

  constructor(private imageService: fotoService, private sanitizer: DomSanitizer) { }

  onFetchImage(): void {
    const imageId = 1; // Cambia l'ID secondo le tue esigenze
    this.imageService.getImageById(imageId).subscribe(response => {
      const objectURL = URL.createObjectURL(response);
      this.imageUrl = this.sanitizer.bypassSecurityTrustUrl(objectURL);
    });
  }
}
