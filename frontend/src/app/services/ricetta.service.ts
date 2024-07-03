import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ricetta } from '../models/ricetta.model';
import {Ricette} from "../endpoints";
import {fotoService} from "./foto.service";

@Injectable({
  providedIn: 'root'
})

export class RicettaService {

  private baseUrl = 'http://localhost:8080/api/ricette';

  constructor(private http: HttpClient, private imageService : fotoService) {
  }

  getAllRicette(): Observable<Ricetta[]> {
    return this.http.get<Ricetta[]>(this.baseUrl);
  }

  getRicetteMadeByUser(userId: number): Observable<Ricetta[]> {
    return this.http.get<Ricetta[]>(this.baseUrl + "/madeby/" + userId);
  }

  getRicettaById(id: number): Observable<Ricetta> {
    return this.http.get<Ricetta>(this.baseUrl + "/" + id);
  }

  deleteRicetta(id: number): Observable<void> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.delete<void>(url);
  }

  modifyRicetta(ricetta: Ricetta, image1: File, image2: File, image3: File): any {
    {
      const headers = new HttpHeaders({'Content-Type': 'application/json'});
      const body = JSON.stringify(ricetta);
      const url = `${this.baseUrl}/${ricetta.id}`;
      this.http.post(url, body, {headers})
        .subscribe({
          next: (response: any) => {
            console.log('Dati inviati con successo!');
            const idRicetta = response.id;
            if (idRicetta) {
              if (image1) {
                this.imageService.uploadImageForRecipeWithIndex(idRicetta, image1, 0)
                  .subscribe(response => {
                    console.log('Upload successful', response);
                  });
              }
              if (image2) {
                this.imageService.uploadImageForRecipeWithIndex(idRicetta, image2, 1)
                  .subscribe(response => {
                    console.log('Upload successful', response);
                  });
              }
              if (image3) {
                this.imageService.uploadImageForRecipeWithIndex(idRicetta, image3, 2)
                  .subscribe(response => {
                    console.log('Upload successful', response);
                  });
              }
            }
            return true;
          },
          error: (error) => {
            console.error('Errore durante l\'invio dei dati:', error);
            return false;
          }
        });
    }
  }

  uploadRicetta(ricetta: Ricetta, image1: File, image2: File, image3: File) {
    const url = 'http://localhost:8080/api/ricette/addricetta';
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const body = JSON.stringify(ricetta);

    this.http.post(url, body, { headers })
      .subscribe({
        next: (response: any) => {
          console.log('Dati inviati con successo!');
          const idRicetta = response.id;
          if (idRicetta) {
            if (image1) {
              this.imageService.uploadImageForRecipeWithIndex(idRicetta, image1,0).subscribe();
            }
            if (image2) {
              this.imageService.uploadImageForRecipeWithIndex(idRicetta, image2,1).subscribe();
            }
            if (image3){
              this.imageService.uploadImageForRecipeWithIndex(idRicetta, image3,2).subscribe();
            }
          }
        },
        error: (error) => {
          console.error('Errore durante l\'invio dei dati:', error);
        }
      });
  }
}
