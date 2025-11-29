import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cementerio } from '../models/cementerio.interface';

// Asegúrate de que esta URL base sea correcta
const API_URL = 'http://localhost:8080/api/v1/cementerios'; 

@Injectable({
  providedIn: 'root'
})
export class CementerioService {

  constructor(private http: HttpClient) { }

  // Método existente para obtener todos los cementerios
  obtenerCementerios(): Observable<Cementerio[]> {
    return this.http.get<Cementerio[]>(API_URL);
  }

  // 🚨 MÉTODO NUEVO para el componente DetalleCementerioComponent
  getById(id: number): Observable<Cementerio> {
    // Llama al endpoint de Spring Boot /api/v1/cementerios/{id}
    const url = `${API_URL}/${id}`;
    return this.http.get<Cementerio>(url);
  }
}