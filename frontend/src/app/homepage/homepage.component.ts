import { Component, OnInit } from '@angular/core';
import { Ricetta } from '../models/ricetta.model';
import { RicettaService } from '../services/ricetta.service';
import {NgForOf, NgIf, NgOptimizedImage} from "@angular/common";
import {fotoLoaderBase64} from "../services/foto-loader-base64.service";

@Component({
  selector: 'app-ricette',
  templateUrl: './homepage.component.html',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    NgOptimizedImage,
  ],
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {

  ricette!: Ricetta[];

  constructor(private ricettaService: RicettaService, private imageLoaderService: fotoLoaderBase64) { }

  ngOnInit(): void {
    this.caricaRicette();
  }

  caricaRicette(): void {
    this.ricettaService.getAllRicette()
      .subscribe(
        (data) => {
          this.ricette = data.map(ricetta => ({
            ...ricetta,
            immagineUrl: ricetta.immagine1 ? this.loadImage(ricetta.immagine1) : '',
          }));
        },
        (error) => {
          console.error('Errore nel caricamento delle ricette:', error);
        }
      );
  }

  loadImage(image :string) {
    return this.imageLoaderService.convertiBase64(image);
  }
}
