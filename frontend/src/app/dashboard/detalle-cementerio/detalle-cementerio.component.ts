import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; 
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { CementerioService } from '../../services/cementerio.service';
import { CementerioDetalle } from '../../models/cementerio-detalle.interface';
import { catchError, of } from 'rxjs';

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
    private cementerioService: CementerioService
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