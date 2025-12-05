import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http'; 
import { Observable } from 'rxjs';
// Asume que estas interfaces existen en 'models'
import { Cementerio } from '../models/cementerio.interface'; 
import { CementerioDetalle } from '../models/cementerio-detalle.interface'; 

// URL base del backend
const API_URL = 'http://localhost:8080/api/v1/cementerios'; 

@Injectable({
  providedIn: 'root'
})
export class CementerioService {

  constructor(private http: HttpClient) { }

  // Obtiene cementerios filtrados por el ID y Rol del usuario
  obtenerCementeriosPorUsuario(usuarioId: number, rol: string): Observable<Cementerio[]> {
    // Construye los parámetros de consulta que Spring Boot espera
    let params = new HttpParams()
      .set('usuarioId', usuarioId.toString())
      .set('rol', rol); 

    // Llama al endpoint GET /api/v1/cementerios?usuarioId=X&rol=Y
    return this.http.get<Cementerio[]>(API_URL, { params: params });
  }
  
  // Método para obtener el detalle completo de un cementerio
  obtenerDetallePorId(id: number): Observable<CementerioDetalle> {
    const url = `${API_URL}/${id}`;
    return this.http.get<CementerioDetalle>(url);
  }
}