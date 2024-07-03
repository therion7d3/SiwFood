import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class fotoService {

  private apiUrl = 'http://localhost:8080/foto';

  constructor(private http: HttpClient) { }

  getImageById(id: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/${id}`, { responseType: 'blob' });
  }

  getImageByUserId(userId: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/utente/${userId}`, { responseType: 'blob' });
  }

  uploadImageForUser(userId: number, file: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('file', file);
    return this.http.post(`${this.apiUrl}/upload/utente/${userId}`, formData);
  }

  uploadImageForRecipe(recipeId: number, file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(`${this.apiUrl}/upload/ricetta/${recipeId}`, formData);
  }

  uploadImageForRecipeWithIndex(recipeId: number, file: File, index: number): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('index', index.toString()); // Se necessario, aggiungi l'indice come parte del FormData
    return this.http.post(`${this.apiUrl}/upload/ricetta/${recipeId}`, formData);
  }

  deleteImage(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
