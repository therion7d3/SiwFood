import { Component } from '@angular/core';
import {Ricetta} from "../models/ricetta.model";
import {NgForOf, NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {AuthService} from "../services/auth.service";
import {RicettaService} from "../services/ricetta.service";
import {Router, RouterModule} from "@angular/router";

@Component({
  selector: 'app-nuovaricetta',
  standalone: true,
  imports: [
    NgIf,
    FormsModule,
    NgForOf
  ],
  templateUrl: './nuovaricetta.component.html',
  styleUrl: './nuovaricetta.component.css'
})
export class NuovaricettaComponent {
  ricetta: Ricetta = {
    authorId: this.authService.getUserId() ?? 0,
    authorName: '',
    description: '',
    immagine1: '',
    immagine2: '',
    immagine3: '',
    listaIngredienti: [],
    title: '',
    immagineUrl: '',
    immagineUrl2: '',
    immagineUrl3: ''
  }
  formSubmitted: boolean = false;
  success: boolean = false;
  file1 : any;
  file2 : any;
  file3 : any;
  successMessage: string = 'La ricetta è stata salvata correttamente';

  constructor(private router: Router ,private ricettaService: RicettaService, protected authService: AuthService) {}

  onImageSelected(event: any, index: number): void {
    const fileList: FileList = event.target.files;
    if (fileList.length > 0) {
      const file: File = fileList[0];
      if (index === 1) {
        this.file1 = file;
        this.readURL(file, 'immagineUrl');
      } else if (index === 2) {
        this.file2 = file;
        this.readURL(file, 'immagineUrl2');
      } else if (index === 3) {
        this.file3 = file;
        this.readURL(file, 'immagineUrl3');
      }
    }
  }

  readURL(file: File, property: string): void {
    const reader = new FileReader();
    reader.onload = (e: any) => {
      this.ricetta[property] = e.target.result;
    };
    reader.readAsDataURL(file);
  }

  aggiungiIngrediente() {
    this.ricetta.listaIngredienti.push({nome: '', quantita: ''});
  }

  rimuoviIngrediente(index: number) {
    this.ricetta.listaIngredienti.splice(index, 1);
  }

  submitForm() {
    this.formSubmitted = true;
    this.ricettaService.uploadRicetta(this.ricetta, this.file1, this.file2, this.file3);
    setTimeout(() => {this.router.navigate(['/']).then();}, 2000);
  }
}

