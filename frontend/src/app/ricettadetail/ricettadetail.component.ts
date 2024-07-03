import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Location, NgForOf, NgIf} from '@angular/common';
import { RicettaService } from '../services/ricetta.service';
import { Ricetta } from '../models/ricetta.model';
import {fotoLoaderBase64} from "../services/foto-loader-base64.service";
import {FormsModule, NgForm} from "@angular/forms";
import {AuthService} from "../services/auth.service";

@Component({
  selector: 'app-ricettadetail',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    FormsModule
  ],
  templateUrl: './ricettadetail.component.html',
  styleUrl: './ricettadetail.component.css'
})
export class RicettadetailComponent implements OnInit{
  ricettaModificata: Ricetta = {
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
  };
  ricetta!: Ricetta;
  editMode = false;
  formSubmitted : boolean = false;
  success: boolean = false;
  tipiQuantita: string[] = ['chilogrammi', 'litri', 'qb', 'pezzi', ' '];
  imagePreview: string | ArrayBuffer | null = null;
  file1 : any;
  file2 : any;
  file3 : any;


  constructor(
    private route: ActivatedRoute,
    private ricettaService: RicettaService,
    private imageLoaderService : fotoLoaderBase64,
    private authService : AuthService,
    private router: Router
  ) {}


  ngOnInit(): void {
    this.getRicettaId();
  }

  getRicettaId(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam !== null && idParam !== undefined) {
      const id = +idParam;
      if (!isNaN(id)) {
        this.caricaRicetta(id);
      } else {
        console.error('L\'ID non è un numero valido');
      }
    } else {
      console.error('L\'ID non è presente nei parametri della route');
    }
  }

  caricaRicetta(id: number): void {
    this.ricettaService.getRicettaById(id)
      .subscribe(
        (data) => {
          this.ricetta = {
            ...data,
            immagineUrl: data.immagine1 ? this.loadImage(data.immagine1) : '',
            immagineUrl2: data.immagine2 ? this.loadImage(data.immagine2) : '',
            immagineUrl3: data.immagine3 ? this.loadImage(data.immagine3) : ''
          };

          this.ricettaModificata = { ...this.ricetta };
        },
        (error) => {
          console.error('Errore nel caricamento della ricetta:', error);
        }
      );
  }

  loadImage(image :string) {
    return this.imageLoaderService.convertiBase64(image);
  }

  toggleEditMode(): void {
    this.editMode = !this.editMode;
  }

  isAuthor(): boolean {
    const currentUserId = this.authService.getUserId();
    return (this.ricetta?.authorId === currentUserId || (this.authService.getRole() === 'admin'));
  }

  saveChanges(): void {
    console.log('Modifiche salvate:', this.ricetta);
    this.editMode = false;
    this.ricettaService.modifyRicetta(this.ricettaModificata, this.file1, this.file2, this.file3);
    this.ngOnInit();
  }

  cancelEdit(): void {
    this.editMode = false;
    this.getRicettaId();
  }

  addIngredient(): void {
    if (this.ricetta) {
      this.ricetta.listaIngredienti.push({ nome: '', quantita: '', tipoQuantita: '' });
    }
  }

  removeIngredient(index: number): void {
    if (this.ricetta && this.ricetta.listaIngredienti.length > index) {
      this.ricetta.listaIngredienti.splice(index, 1);
    }
  }

  isFormValid(form: NgForm): boolean {
    if (!form) {
      return false;
    }
    for (const controlName of Object.keys(form.controls)) {
      const control = form.controls[controlName];
      if (control.invalid || control.value === '') {
        return false;
      }
    }
    return true;
  }

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
      this.ricettaModificata[property] = e.target.result;
    };
    reader.readAsDataURL(file);
  }

  eliminaRicetta(): void {
    const id = this.ricetta.id;
    if (id != null)
    this.ricettaService.deleteRicetta(id).subscribe();
    this.router.navigate(['/']);
  }
}
