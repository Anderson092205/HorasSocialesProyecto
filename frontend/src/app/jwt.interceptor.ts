import { HttpInterceptorFn, HttpErrorResponse } from "@angular/common/http";
import { inject } from "@angular/core";
import { AuthService } from "./auth.service";
import { catchError, throwError } from 'rxjs';
import { Router } from "@angular/router"; // 🚨 Necesario para la redirección

export const jwtInterceptor: HttpInterceptorFn = (req, next) => {
    const authService = inject(AuthService);
    const router = inject(Router); // Inyectamos el Router
    const token = authService.getToken(); 

    // Excluir la URL de login
    if (req.url.includes('auth') || req.url.includes('login')) {
        return next(req);
    }
    
    let clonedReq = req;

    if (token) {
        // Clonamos y añadimos el token si existe
        clonedReq = req.clone({
            setHeaders: {
                Authorization: `Bearer ${token}` 
            }
        });
    }

    // 🚨 PASO CLAVE: Capturar la respuesta y manejar el 401
    return next(clonedReq).pipe(
      catchError((error: HttpErrorResponse) => {
        // Verificar si es un error de autorización (401)
        if (error.status === 401) {
          console.error('Token caducado o no válido. Forzando cierre de sesión.');
          
          // 1. Forzar el cierre de sesión (borrar el token de Local Storage)
          authService.logout();
          
          // 2. Redirigir al usuario a la página de login
          router.navigate(['/login']);
          
          // 3. Devolver un observable con el error para que el componente lo sepa
          return throwError(() => new Error('Sesión expirada. Redirigiendo a login.'));
        }
        
        // Para cualquier otro error (500, 404, etc.), simplemente propagarlo.
        return throwError(() => error);
      })
    );
};