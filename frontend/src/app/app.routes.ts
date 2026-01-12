import { Routes } from '@angular/router';
import { LoginComponent } from './login.component';
import { CementeriosComponent } from './dashboard/cementerios/cementerios.component';
import { DetalleCementerioComponent } from './dashboard/detalle-cementerio/detalle-cementerio.component';
import { authGuard } from './auth.guard'; 
import { CrearUsuarioComponent } from './gestion-usuarios/crear-usuario.component';
// IMPORTACIÓN NUEVA
import { ActualizarPasswordComponent } from './gestion-usuarios/actualizar-password.component';

export const routes: Routes = [
  
  // 1. Ruta de Autenticación (Pública)
  { path: 'login', component: LoginComponent },

  // 2. RUTA NUEVA: Cambio de contraseña temporal (Pública)
  // Se deja pública para que el usuario pueda acceder sin Token JWT tras el primer login
  { path: 'actualizar-password', component: ActualizarPasswordComponent },
  
  // 3. Ruta del Listado (PROTEGIDA)
  { 
    path: 'cementerios', 
    component: CementeriosComponent,
    canActivate: [authGuard] 
  },
  
  // 4. Ruta del Detalle (PROTEGIDA Y CON PARÁMETRO)
  { 
    path: 'detalle-cementerio/:id', 
    component: DetalleCementerioComponent,
    canActivate: [authGuard] 
  },

  // 5. Gestión de Usuarios (PROTEGIDA)
  { 
    path: 'usuarios/crear', 
    component: CrearUsuarioComponent,
    canActivate: [authGuard],
    data: { role: 'INFORMATICA' } 
  },
  
  // 6. Redirección Inicial y Wildcard
  // Cambiado: Ahora redirige a /login por defecto para asegurar el flujo de entrada
  { path: '', redirectTo: '/login', pathMatch: 'full' }, 
  
  // Cualquier otra URL no definida redirige al login
  { path: '**', redirectTo: '/login' } 
];