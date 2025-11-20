import { Component, OnInit } from '@angular/core';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms'; 
import { CommonModule } from '@angular/common'; 
import { HttpClientModule } from '@angular/common/http'; 

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule, HttpClientModule], 
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  correo = '';
  password = '';
  errorMessage: string = '';
  rememberMe: boolean = false; // Nueva variable

  // Clave para localStorage
  private readonly REMEMBER_KEY = 'remember_username';

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    // 1. Cargar usuario recordado al iniciar
    const savedUsername = localStorage.getItem(this.REMEMBER_KEY);
    if (savedUsername) {
      this.correo = savedUsername;
      this.rememberMe = true; // Marca el checkbox si hay usuario guardado
    }
    
    // Redirige si ya está logueado
    if (this.authService.isLoggedIn()) {
      this.router.navigate(['/home']);
    }
  }

  onLogin(): void {
    this.errorMessage = ''; 

    // 2. Lógica de guardar/eliminar usuario antes de intentar el login
    if (this.rememberMe) {
      localStorage.setItem(this.REMEMBER_KEY, this.correo); // Guarda el correo
    } else {
      localStorage.removeItem(this.REMEMBER_KEY); // Elimina si el checkbox está desmarcado
    }

    this.authService.login(this.correo, this.password).subscribe({
      next: (response: any) => {
        this.authService.setToken(response.jwt);
        this.router.navigate(['/home']); 
      },
      error: (err) => {
        this.errorMessage = 'Credenciales no válidas. Por favor, verifica tu correo y contraseña.';
        console.error('Error de autenticación:', err);
      }
    });
  }
}