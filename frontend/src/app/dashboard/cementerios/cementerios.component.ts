import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; 
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Router } from '@angular/router'; 
// Se mantienen imports de catchError y of por si se usan en la lógica del componente o para futuras expansiones
import { catchError, of } from 'rxjs'; 

import { CementerioService } from '../../services/cementerio.service'; 
import { Cementerio } from '../../models/cementerio.interface'; 
import { AuthService } from '../../auth.service';
import { LanguageService } from '../../services/language.service'; // Incluido para la traducción

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
  
  // Variables para el filtro de permisos (de la versión 1)
  usuarioId: number | null = null;
  rolUsuario: string | null = null;

  // Variable para el idioma (de la versión 2)
  lang = 'es'; 

  constructor(
    private cementerioService: CementerioService,
    private authService: AuthService,
    private router: Router,
    public languageService: LanguageService // Inyectado para la gestión de idioma
  ) { }

  ngOnInit(): void {
    // 1. Suscripción al idioma (de la versión 2)
    this.languageService.currentLang$.subscribe(l => this.lang = l);
    
    // 2. Carga de datos filtrada por permisos (de la versión 1)
    this.cargarCementeriosFiltrados();
  }

  cargarCementeriosFiltrados(): void {
    this.usuarioId = this.authService.getUserId();
    this.rolUsuario = this.authService.getUserRole();
    this.cargando = true;

    if (!this.usuarioId || !this.rolUsuario) {
        this.errorCarga = 'Faltan datos de sesión. Redirigiendo...';
        // Se mantiene la redirección con un pequeño delay
        setTimeout(() => {
            this.authService.logout();
            this.router.navigate(['/login']);
        }, 1000); 
        return;
    }
    
    this.cementerioService.obtenerCementeriosPorUsuario(this.usuarioId, this.rolUsuario).subscribe({
      next: (data) => {
        this.cementerios = data;
        this.cargando = false;
        this.errorCarga = null;
      },
      error: (err) => {
        console.error('Error al cargar cementerios filtrados:', err);
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
    // Se añade la navegación de la versión 2
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
  
  // Métodos de gestión de idioma (de la versión 2)
  cambiarIdioma(): void {
    const nuevo = this.lang === 'es' ? 'en' : 'es';
    this.languageService.setLanguage(nuevo);
  }

  t(key: string) {
    return this.languageService.translate(key);
  }
}

