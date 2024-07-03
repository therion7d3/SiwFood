import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterOutlet, RouterLink, RouterLinkActive} from '@angular/router';
import {NgForOf, NgIf} from "@angular/common";
import {HomeComponent} from "./home/home.component";
import { AuthService } from "./services/auth.service";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [HomeComponent, CommonModule, RouterOutlet, RouterLink, RouterLinkActive, NgForOf, NgIf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})

export class AppComponent implements OnInit {
  title = 'siwfood';
  username: string | null = '';
  userId: number | null = null;

  constructor(protected authService: AuthService) {};

  ngOnInit(): void {
    this.username = this.authService.getUsername();
    this.userId = this.authService.getUserId();
  }

  logout() {
    this.authService.logout();
  }
}
