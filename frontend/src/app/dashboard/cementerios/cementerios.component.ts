import { Component, OnInit } from '@angular/core';

import { CommonModule } from '@angular/common'; 
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Router } from '@angular/router'; 

import { CementerioService } from '../../services/cementerio.service'; 
import { Cementerio } from '../../models/cementerio.interface'; 
import { AuthService } from '../../auth.service'; // 🚨 RUTA CORREGIDA: Sube dos niveles y accede a auth.service.ts

@Component({
  selector: 'app-cementerios',
  standalone: true,
  imports: [
    CommonModule, 
    HttpClientModule,
    RouterModule 
  ],
  templateUrl: './cementerios.component.html',
  styleUrl: './cementerios.component.css'
})
export class CementeriosComponent implements OnInit {

  cementerios: Cementerio[] = [];
  cargando: boolean = true;
  errorCarga: string | null = null;

  // Constructor que fusiona todas las dependencias
  constructor(
    private cementerioService: CementerioService,
    private authService: AuthService, // Agregado para logout
    private router: Router // Agregado para navegación
  ) { }

  ngOnInit(): void {
    this.cargarCementerios();
  }

  cargarCementerios(): void {
    this.cementerioService.obtenerCementerios().subscribe({
      next: (data) => {
        this.cementerios = data;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al cargar cementerios:', err);
        this.errorCarga = 'Error al conectar con el servidor. Por favor, verifica el backend.';
        this.cargando = false;
      }
    });
  }

  // MÉTODO PARA CERRAR SESIÓN
  cerrarSesion(): void {
    this.authService.logout();
    this.router.navigate(['/login']); // Redirige a la página de login
  }

  getCardClass(tipo: string): string {
    // Lógica para asignar clases CSS según el tipo de cementerio
    if (tipo && tipo.toLowerCase().includes('general')) {
      return 'border-primary'; 
    } else if (tipo && tipo.toLowerCase().includes('jardin')) {
      return 'border-success'; 
    }
    return '';
  }
}