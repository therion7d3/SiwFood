import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {Ricetta} from "../models/ricetta.model";
import {RicettaService} from "../services/ricetta.service";
import {NgForOf, NgIf, NgOptimizedImage} from "@angular/common";
import {AuthService} from "../services/auth.service";
import {fotoLoaderBase64} from "../services/foto-loader-base64.service";

@Component({
  selector: 'app-myrecipes',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    NgOptimizedImage
  ],
  templateUrl: './myrecipes.component.html',
  styleUrl: './myrecipes.component.css'
})
export class MyrecipesComponent implements OnInit {
  userId!: number;
  ricette!: Ricetta[];
  selectedRicetta: Ricetta | null = null;

  constructor(private ricettaService: RicettaService, private authService: AuthService, private imageLoaderService: fotoLoaderBase64) {
  }

  ngOnInit(): void {
    this.caricaRicette();
  }

  caricaRicette(): void {
    const userId = this.authService.getUserId();
    if (userId) {
      this.ricettaService.getRicetteMadeByUser(userId)
        .subscribe(
          (data) => {
            this.ricette = data.map(ricetta => ({
              ...ricetta,
              immagineUrl: this.loadImage(ricetta.immagine1)
            }));
          },
          (error) => {
            console.error('Errore nel caricamento delle ricette:', error);
          }
        );
    }
  }

  loadImage(image: string) {
    return this.imageLoaderService.convertiBase64(image);
  }

  deleteRicetta(id: number | undefined): void {
    if (id === undefined) {
      console.error('ID della ricetta non definito.');
      return;
    }


    this.ricettaService.deleteRicetta(id).subscribe(
      () => {
        // Rimuovi la ricetta dall'array locale
        this.ricette = this.ricette.filter((ricetta) => ricetta.id !== id);
        console.log('Ricetta eliminata con successo.');
      },
      (error) => {
        console.error('Errore durante l\'eliminazione della ricetta:', error);
      }
    );
  }
}
