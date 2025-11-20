import { Component } from '@angular/core';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  template: `
    <div class="home-container">
      <h1>¡Bienvenido al Cementerio Digital!</h1>
      <p>Has iniciado sesión exitosamente.</p>
      <button (click)="logout()">Cerrar Sesión</button>
      <p style="margin-top: 20px;">Tu token JWT está almacenado.</p>
    </div>
  `,
  styles: [`
    .home-container {
      text-align: center;
      padding-top: 50px;
      background-color: #f8f8f8;
      height: 100vh;
      color: #333;
    }
    button {
      padding: 10px 20px;
      background-color: #e91e63; /* Fucsia */
      color: white;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      font-weight: bold;
      transition: background-color 0.3s;
    }
    button:hover {
        background-color: #ad1457;
    }
  `]
})
export class HomeComponent {
  constructor(public authService: AuthService, private router: Router) { }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}