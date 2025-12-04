import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; 
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Router } from '@angular/router'; 

import { CementerioService } from '../../services/cementerio.service'; 
import { Cementerio } from '../../models/cementerio.interface'; 
import { AuthService } from '../../auth.service'; 

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
  
  // Variables para el filtro de permisos
  usuarioId: number | null = null;
  rolUsuario: string | null = null;

  constructor(
    private cementerioService: CementerioService,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.cargarCementeriosFiltrados();
  }

  cargarCementeriosFiltrados(): void {
    this.usuarioId = this.authService.getUserId();
    this.rolUsuario = this.authService.getUserRole();
    this.cargando = true;

    if (!this.usuarioId || !this.rolUsuario) {
        this.errorCarga = 'Faltan datos de sesión. Redirigiendo...';
        setTimeout(() => {
            this.authService.logout();
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
}