import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; 
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Router } from '@angular/router'; 
import { catchError, of } from 'rxjs'; 

import { CementerioService } from '../../services/cementerio.service'; 
import { Cementerio } from '../../models/cementerio.interface'; 
import { AuthService } from '../../auth.service';
import { LanguageService } from '../../services/language.service'; 

@Component({
  selector: 'app-cementerios',
  standalone: true,
  imports: [
    CommonModule, 
    HttpClientModule,
    RouterModule 
  ],
  templateUrl: './cementerios.component.html',
  styleUrls: ['./cementerios.component.css']
})
export class CementeriosComponent implements OnInit {

  cementerios: Cementerio[] = []; 
  cargando: boolean = true;
  errorCarga: string | null = null;
  
  // Variable para el idioma (de la versión 2)
  lang = 'es'; 

  constructor(
    private cementerioService: CementerioService,
    private authService: AuthService,
    private router: Router,
    public languageService: LanguageService
  ) { }

  ngOnInit(): void {
    // 1. Suscripción al idioma
    this.languageService.currentLang$.subscribe(l => this.lang = l);
    
    // 2. Carga de datos filtrada por permisos (ahora delegada al backend)
    this.cargarCementeriosFiltrados();
  }

  cargarCementeriosFiltrados(): void {
    // ⭐ Se eliminó la validación manual de usuarioId y rolUsuario
    this.cargando = true;

    // La llamada es simple, el Interceptor adjunta el token y el backend filtra.
    this.cementerioService.obtenerCementeriosPorUsuario().subscribe({
      next: (data) => {
        this.cementerios = data;
        this.cargando = false;
        this.errorCarga = null;
      },
      error: (err) => {
        console.error('Error al cargar cementerios filtrados:', err);
        // Los errores 401 (no autenticado) son manejados por el Interceptor.
        // Aquí manejamos otros errores de la llamada.
        if (err.status === 403) {
            this.errorCarga = 'No tiene permisos para ver esta lista.';
        } else {
            this.errorCarga = 'Error al cargar la lista de cementerios. Verifica la conexión.';
        }
        this.cargando = false;
      }
    });
  }
  
  cerrarSesion(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  getCardClass(tipo: string): string {
    if (tipo && tipo.toLowerCase().includes('general')) {
      return 'border-blue-500'; 
    } else if (tipo && tipo.toLowerCase().includes('jardin')) {
      return 'border-green-500'; 
    }
    return 'border-gray-300';
  }
  
  verDetalle(id: number): void {
      this.router.navigate(['/cementerios', id]);
  }
  
  // Métodos de gestión de idioma
  cambiarIdioma(): void {
    const nuevo = this.lang === 'es' ? 'en' : 'es';
    this.languageService.setLanguage(nuevo);
  }

  t(key: string) {
    return this.languageService.translate(key);
  }
}