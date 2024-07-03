import {Component, OnInit} from '@angular/core';
import { FormsModule } from "@angular/forms";
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import {fotoService} from "../../services/foto.service";
import {DomSanitizer} from "@angular/platform-browser";
import {AppComponent} from "../../app.component";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})

export class LoginComponent implements OnInit{
  username: string = '';
  password: string = '';
  message: string = '';
  success: boolean = false;
  requested: boolean = false;
  errorMessage: string = '';
  imageUrl: any;

  constructor(private appComponent: AppComponent, private authService: AuthService, private router: Router, private imageService: fotoService, private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    const imageId = 1;
    this.imageService.getImageById(imageId).subscribe(response => {
      const objectURL = URL.createObjectURL(response);
      this.imageUrl = this.sanitizer.bypassSecurityTrustUrl(objectURL);
    });
  }

  login() {
    this.authService.login(this.username, this.password).subscribe(
      response => {
        this.message = response.message;
        this.success = response.success;
        this.requested = true;
        if (this.success) {
          localStorage.setItem('token', response.token);
          localStorage.setItem('username', response.username);
          localStorage.setItem('userId', response.userId);
          localStorage.setItem('role', response.role[0].name);
          this.appComponent.ngOnInit();
          setTimeout(() => {
            this.router.navigate(['/']).then(r => this.success && this.requested);
          }, 2000);
        }
      },
      error => {
        this.errorMessage = error.error.message;
        this.requested = true;
        this.success = false;
      }
    );
  }
}
