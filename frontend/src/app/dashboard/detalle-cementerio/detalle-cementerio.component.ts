import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; 
import { ActivatedRoute, Router } from '@angular/router'; // Para leer el ID y navegar
import { HttpClientModule } from '@angular/common/http';
import { CementerioService } from '../../services/cementerio.service';
import { Cementerio } from '../../models/cementerio.interface'; 
import { catchError, of } from 'rxjs'; // Importar catchError y of para manejo de errores

@Component({
  selector: 'app-detalle-cementerio',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  // 🚨 CORRECCIÓN: Usar templateUrl/styleUrls en lugar de template/styles inline
  templateUrl: './detalle-cementerio.component.html',
  styleUrls: ['./detalle-cementerio.component.css']
})
export class DetalleCementerioComponent implements OnInit {
  
  cementerioId: number | null = null;
  cementerio: Cementerio | null = null;
  cargando: boolean = true;
  errorCarga: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private cementerioService: CementerioService // Asumo que existe este servicio
  ) { }

  ngOnInit(): void {
    // 1. Obtener el parámetro 'id' de la URL
    const idParam = this.route.snapshot.paramMap.get('id');
    
    if (idParam) {
      this.cementerioId = +idParam; // Convierte el string ID a número
      this.cargarDetalleCementerio(this.cementerioId);
    } else {
      this.errorCarga = "ID de cementerio no encontrado en la URL.";
      this.cargando = false;
    }
  }

  cargarDetalleCementerio(id: number): void {
    this.cargando = true;
    this.errorCarga = null;
    
    // 2. Llama al servicio para obtener los detalles del backend
    // 🚨 Mejoramos el manejo de errores en el subscribe con catchError
    this.cementerioService.getById(id).pipe(
        catchError(err => {
            this.errorCarga = `No se pudo cargar el detalle del cementerio ID ${id}. Verifica el backend y el endpoint /api/v1/cementerios/{id}.`;
            this.cargando = false;
            console.error('Error al cargar detalle:', err);
            return of(null); // Retorna un Observable vacío para detener la cadena
        })
    )
    .subscribe({
      next: (data: Cementerio | null) => {
        if (data) {
            this.cementerio = data;
        } else {
            // Si el error fue manejado en pipe, solo ajustamos el estado de carga
            if (!this.errorCarga) {
                this.errorCarga = `No se encontraron datos para el cementerio ID ${id}.`;
            }
        }
        this.cargando = false;
      }
    });
  }

  // Función para volver a la página de listado
  goBack(): void {
    this.router.navigate(['/cementerios']);
  }
}