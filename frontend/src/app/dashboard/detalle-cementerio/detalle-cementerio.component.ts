import { Component, OnInit, Injectable } from '@angular/core';
import { CommonModule } from '@angular/common'; 
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { catchError, of, Observable } from 'rxjs';

// --- MOCK INTERFACES Y SERVICIO (Para la funcionalidad en un entorno aislado) ---

// 1. Interfaz del Detalle (Simulada)
export interface CementerioDetalle {
    id: number;
    nombre: string;
    ubicacion: string;
    tipo: string; 
    datosTabla: string[];
}

// 2. Servicio Mock (Simulando CementerioService)
@Injectable({ providedIn: 'root' })
export class CementerioService {
    // Es necesario inyectar HttpClient en el constructor si se usa en la app real
    constructor(private http: HttpClient) {} 
    
    // Simula la llamada HTTP
    obtenerDetallePorId(id: number): Observable<CementerioDetalle> {
        const mockData: CementerioDetalle = {
            id: id,
            nombre: `Cementerio Histórico Nº ${id}`,
            ubicacion: 'Avenida Siempre Viva 742',
            tipo: (id % 2 === 0) ? 'General' : 'Jardín',
            datosTabla: Array(10).fill(0).map((_, i) => `Metadato ${i + 1}`)
        };
        // Retorna un Observable con los datos simulados
        return of(mockData); 
    }
}
// ----------------------------------------------------------------------------------

@Component({
  selector: 'app-detalle-cementerio',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './detalle-cementerio.component.html',
  styleUrls: ['./detalle-cementerio.component.css']
})
export class DetalleCementerioComponent implements OnInit {

  columnas = Array(10).fill(0);

  cementerioId: number | null = null;
  cementerioDetalle: CementerioDetalle | null = null; // CLAVE
  cargando: boolean = true;
  errorCarga: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private cementerioService: CementerioService,
    // Se requiere el HttpClient para el mock del servicio
    private http: HttpClient 
  ) { }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    
    if (idParam) {
      this.cementerioId = +idParam;
      this.cargarDetalleCementerio(this.cementerioId);
    } else {
      this.errorCarga = "ID de cementerio no encontrado en la URL.";
      this.cargando = false;
    }
  }

  cargarDetalleCementerio(id: number): void {
    this.cargando = true;
    this.errorCarga = null;

    // Suscripción al servicio
    this.cementerioService.obtenerDetallePorId(id).pipe(
      catchError(err => {
        this.errorCarga = `No se pudo cargar el detalle del cementerio ID ${id}.`;
        this.cargando = false;
        console.error('Error al cargar detalle:', err);
        return of(null);
      })
    )
    .subscribe({
      next: (data: CementerioDetalle | null) => {
        if (data) {
          this.cementerioDetalle = data;
        } else {
          if (!this.errorCarga) {
            this.errorCarga = `No se encontraron datos para el cementerio ID ${id}.`;
          }
        }
        this.cargando = false;
      }
    });
  }

  goBack(): void {
    this.router.navigate(['/cementerios']);
  }
}