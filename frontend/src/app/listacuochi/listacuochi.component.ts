import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {utentiService} from "../services/utenti.service";
import {fotoService} from "../services/foto.service";
import {DomSanitizer} from "@angular/platform-browser";
import {NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-listacuochi',
  standalone: true,
  imports: [
    NgIf,
    NgForOf
  ],
  templateUrl: './listacuochi.component.html',
  styleUrl: './listacuochi.component.css'
})
export class ListacuochiComponent implements OnInit {
  userList: any[] = [];
  imageUrl: any;

  constructor(private http: HttpClient, private userService: utentiService, private imageService: fotoService, private sanitizer: DomSanitizer) { }


  ngOnInit(): void {
    if (typeof window !== "undefined") {
      this.fetchUserList();
    }
  }

  fetchUserList(): void {
    this.userService.getAllUsers().subscribe(
      (response: any[]) => {
        this.userList = response.map(user => ({
          id: user.id,
          email: user.email,
          nome: user.nome,
          cognome: user.cognome,
          dataNascita: user.dob,
          profilePicture: null // Inizialmente nessuna immagine del profilo
        }));

        // Chiamata per ottenere l'immagine del profilo per ciascun utente
        this.userList.forEach(user => {
          this.fetchProfilePicture(user.id);
        });
      },
      error => {
        console.error('Errore durante il recupero della lista utenti', error);
      }
    );
  }

  fetchProfilePicture(userId : number): void {
    this.imageService.getImageByUserId(userId).subscribe(response => {
      const userToUpdate = this.userList.find(user => user.id === userId);
      const objectURL = URL.createObjectURL(response);
      userToUpdate.profilePicture = this.sanitizer.bypassSecurityTrustUrl(objectURL);
    });
  }

}
