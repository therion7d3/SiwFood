import {Component, OnInit} from '@angular/core';
import {NgClass, NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {fotoService} from "../../services/foto.service";
import {DomSanitizer} from "@angular/platform-browser";
import {AppComponent} from "../../app.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    NgIf,
    FormsModule,
    NgClass
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit {
  name: string = '';
  surname: string = '';
  dob: string = '';
  email: string = '';
  password: string = '';
  confirmPassword: string = '';
  emailValid: boolean = true;
  imageUrl: any;
  errore: boolean = false;
  success: boolean = false;

  constructor(private router: Router, private appComponent: AppComponent, private http: HttpClient, private imageService: fotoService, private sanitizer: DomSanitizer) {}

  passwordsMatch(): boolean {
    return this.password === this.confirmPassword;
  }

  validateEmail(email: string): boolean {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  }

  register() {
    this.emailValid = this.validateEmail(this.email);

    if (this.passwordsMatch() && this.emailValid) {
      const userData = {
        nome: this.name,
        cognome: this.surname,
        dob: this.dob,
        email: this.email,
        password: this.password
      };

      this.http.post('http://localhost:8080/api/register', userData).subscribe(
        response => {
          this.appComponent.ngOnInit();
          this.success = true;
          setTimeout(() => {
            this.router.navigate(['/']).then();
          }, 2000);
        },
        error => {
          this.errore = true;
        }
      );
    }
  }

  ngOnInit(): void {
    const imageId = 1; // Cambia l'ID secondo le tue esigenze
    this.imageService.getImageById(imageId).subscribe(response => {
      const objectURL = URL.createObjectURL(response);
      this.imageUrl = this.sanitizer.bypassSecurityTrustUrl(objectURL);
    });
  }
}
