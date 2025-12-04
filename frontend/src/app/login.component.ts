import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
// RUTA CRÍTICA: La ruta debe ser correcta. Si está en la misma carpeta, usa './auth.service'
import { AuthService, AuthResponse } from './auth.service'; 
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
  rememberMe: boolean = false; 
  cargando: boolean = false;

  private readonly REMEMBER_KEY = 'remember_username';

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    // 1. LÓGICA COMPLETA DE RECORDAR: Cargar el nombre de usuario si fue guardado
    const savedUsername = localStorage.getItem(this.REMEMBER_KEY);
    if (savedUsername) {
      this.correo = savedUsername;
      this.rememberMe = true;
    }
    
    // 2. Redirección si ya está logeado
    if (this.authService.isLoggedIn()) {
      this.router.navigate(['/cementerios']); 
    }
  }

  onLogin(): void {
    this.errorMessage = ''; 
    this.cargando = true;

    // 3. LÓGICA COMPLETA DE RECORDAR: Guardar o eliminar el nombre de usuario
    if (this.rememberMe) {
      localStorage.setItem(this.REMEMBER_KEY, this.correo);
    } else {
      localStorage.removeItem(this.REMEMBER_KEY);
    }

    this.authService.login(this.correo, this.password).subscribe({
      next: (response: AuthResponse) => {
        // Usa el método setAuthData del servicio para guardar la sesión
        this.authService.setAuthData(response.token, response.id, response.rol);
        this.cargando = false;
        
        this.router.navigate(['/cementerios']); 
      },
      error: (err) => {
        this.errorMessage = 'Credenciales no válidas o el servidor no responde.';
        console.error('Error de autenticación:', err);
        this.cargando = false;
      }
    });
  }
}