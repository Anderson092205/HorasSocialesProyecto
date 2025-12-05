import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';

// Claves de almacenamiento
const TOKEN_KEY = 'jwt_token';
const USER_ID_KEY = 'user_id';
const USER_ROLE_KEY = 'user_rol';

// Interfaz esperada para el login (asegúrate de que tu backend la cumpla)
export interface AuthResponse {
    token: string;
    id: number; // Mapea a idUsuario
    rol: string; // Mapea al nombre del rol (e.g., "ADMIN", "CONSULTA")
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  // AJUSTA esta URL si es necesario. Se asume v1 en el backend.
  private baseUrl = 'http://localhost:8080/api/auth'; 
  
  constructor(private http: HttpClient, private router: Router) { }

  // Llama al endpoint de login
  login(correo: string, password: string): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.baseUrl}/login`, { correo, password });
  }

  // MÉTODO setAuthData COMPLETO: Guarda Token, ID y Rol
  setAuthData(token: string, userId: number, role: string): void {
    localStorage.setItem(TOKEN_KEY, token);
    localStorage.setItem(USER_ID_KEY, userId.toString());
    localStorage.setItem(USER_ROLE_KEY, role);
  }

  // Obtiene el ID del usuario como número (o null)
  getUserId(): number | null {
    const id = localStorage.getItem(USER_ID_KEY);
    return id ? parseInt(id, 10) : null;
  }

  // Obtiene el Rol del usuario como string (o null)
  getUserRole(): string | null {
    return localStorage.getItem(USER_ROLE_KEY);
  }
  
  getToken(): string | null {
    return localStorage.getItem(TOKEN_KEY);
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  // El logout limpia todos los datos y redirige
  logout(): void {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_ID_KEY);
    localStorage.removeItem(USER_ROLE_KEY);
    this.router.navigate(['/login']);
  }
}