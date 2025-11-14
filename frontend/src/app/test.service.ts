import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TestService {
  private readonly url = 'http://localhost:8080/api/test';

  constructor(private http: HttpClient) {}

  pingBackend(): Observable<string> {
    return this.http.get(this.url, { responseType: 'text' }).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('Error al conectar con el backend:', error.message);
    return throwError(() => new Error('No se pudo conectar con el backend'));
  }
}
