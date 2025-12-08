import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
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
  // Variables del formulario
  correo = '';
  password = '';
  errorMessage: string = '';
  rememberMe: boolean = false; 
  cargando: boolean = false;

  // Constantes para localStorage
  private readonly REMEMBER_KEY = 'remember_username';
  private readonly REMEMBER_ME_STATE_KEY = 'remember_me_state'; 

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    // 1. Redirección si ya está logeado
    if (this.authService.isLoggedIn()) {
      this.router.navigate(['/cementerios']); 
    }

    // 2. LÓGICA DE RECORDAR: Cargar el estado de la casilla SIEMPRE
    const savedRememberState = localStorage.getItem(this.REMEMBER_ME_STATE_KEY);
    this.rememberMe = savedRememberState === 'true'; 

    // 3. LÓGICA DE RECORDAR: Cargar el nombre de usuario SOLO si 'rememberMe' es true
    if (this.rememberMe) {
      const savedUsername = localStorage.getItem(this.REMEMBER_KEY);
      if (savedUsername) {
        this.correo = savedUsername;
      } else {
        // Si la casilla está marcada pero no hay correo, desmarcar la casilla
        this.rememberMe = false;
      }
    }
  }
  
  // 🔑 FUNCIÓN CLAVE: Guarda el correo inmediatamente si la casilla se marca
  onRememberMeChange(): void {
    // Si la casilla está marcada (this.rememberMe es TRUE)
    if (this.rememberMe) {
        // SOLO GUARDAMOS EL CORREO SI EL CAMPO NO ESTÁ VACÍO
        if (this.correo.trim() !== '') {
            localStorage.setItem(this.REMEMBER_KEY, this.correo); // <--- GUARDADO INMEDIATO
        }
        localStorage.setItem(this.REMEMBER_ME_STATE_KEY, 'true');
    } 
    // Si la casilla está desmarcada (this.rememberMe es FALSE)
    else {
        this.correo = ''; 
        localStorage.removeItem(this.REMEMBER_KEY);
        localStorage.setItem(this.REMEMBER_ME_STATE_KEY, 'false');
    }
  }

  onLogin(): void {
    this.errorMessage = ''; 
    this.cargando = true;

    // 4. LÓGICA DE GUARDADO FINAL (Mecanismo de seguridad)
    // Ya que la lógica principal de guardado está en onRememberMeChange,
    // esta sección solo asegura que el estado sea el correcto antes de la llamada API.
    if (this.rememberMe) {
        // Si la casilla está marcada, aseguramos que el valor actual se guarde
        localStorage.setItem(this.REMEMBER_KEY, this.correo);
        localStorage.setItem(this.REMEMBER_ME_STATE_KEY, 'true');
    } else {
        // Si la casilla no está marcada, aseguramos que el correo se elimine.
        localStorage.removeItem(this.REMEMBER_KEY);
        localStorage.setItem(this.REMEMBER_ME_STATE_KEY, 'false');
    }

    // 5. Llamada al servicio de autenticación
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