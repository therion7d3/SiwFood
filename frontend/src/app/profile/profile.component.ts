import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {utentiService} from "../services/utenti.service";
import {fotoService} from "../services/foto.service";
import {DomSanitizer} from "@angular/platform-browser";
import {AuthService} from "../services/auth.service";
import {ActivatedRoute, Route, Router} from "@angular/router";

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    NgIf,
    FormsModule
  ],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {
  userInfo = {
    id: 0,
    email: '',
    nome: '',
    cognome: '',
    dataNascita: ''
  };
  imageUrl: any;
  preview: any;
  selectedFile: any;

  constructor(private router : Router, private route: ActivatedRoute, private authService: AuthService, private http: HttpClient, private userService: utentiService, private imageService: fotoService, private sanitizer: DomSanitizer) { }

  editing = false;

  modifica() {
    this.editing = true;
  }

  isAmministratore(): boolean {
    return (this.authService.getRole() === 'admin');
  }

  isUtenteCorrente(): boolean {
    const currentUserId = this.authService.getUserId();
    return this.userInfo?.id === currentUserId;
  }

  annulla() {
    this.editing = false;
    this.ngOnInit();
  }

  salva() {
    this.editing = false;
    this.http.post<any>('http://localhost:8080/api/utenti/modifica/me', this.userInfo).subscribe();
    if (this.selectedFile && this.userInfo.id) {
      this.imageService.uploadImageForUser(this.userInfo.id, this.selectedFile).subscribe();
    }
  }

  ngOnInit(): void {
    if (typeof window !== "undefined") {
      const idParam = this.route.snapshot.paramMap.get('id');
      if (idParam !== null && idParam !== undefined) {
        const id = +idParam;
        if (!isNaN(id)) {
          this.recuperaInfoUtente(id);
        }
      } else {
        const idMyself = this.authService.getUserId();
        if (idMyself !== null && idMyself !== undefined) {
          const id = +idMyself;
          if (!isNaN(id)) {
            this.recuperaInfoUtente(id);
          }
        }
      }
      this.isUtenteCorrente();
    }
  }

  recuperaInfoUtente(userId : number): void {
    this.userService.getUserById(userId).subscribe(
      response => {
        this.userInfo.id = response.id;
        this.userInfo.email = response.email;
        this.userInfo.nome = response.nome;
        this.userInfo.cognome = response.cognome;
        this.userInfo.dataNascita = response.dob;
        this.recuperaInfoFotoProfilo();
      },
      error => {
      }
    );
  }

  recuperaInfoFotoProfilo(): void {
    this.imageService.getImageByUserId(this.userInfo.id).subscribe(response => {
      const objectURL = URL.createObjectURL(response);
      this.imageUrl = this.sanitizer.bypassSecurityTrustUrl(objectURL);
    });
  }

  eliminaProfilo(){
    this.userService.deleteUserById(this.userInfo.id).subscribe();
    this.router.navigate(['login']).then();
  }

  onFileChange(event: any) {
    const reader = new FileReader();
    if (event.target.files && event.target.files.length) {
      const [file] = event.target.files;
      reader.readAsDataURL(file);
      reader.onload = () => {
        this.preview = reader.result;
        const file: File = event.target.files[0];
        if (file) {
          this.selectedFile = file;
      }
    }
    }
  }
}
