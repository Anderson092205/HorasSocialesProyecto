import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; 
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Router } from '@angular/router'; 

import { CementerioService } from '../../services/cementerio.service'; 
import { Cementerio } from '../../models/cementerio.interface'; 
import { AuthService } from '../../auth.service';
import { LanguageService } from '../../services/language.service';

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

  lang = 'es'; // idioma actual

  constructor(
    private cementerioService: CementerioService,
    private authService: AuthService,
    private router: Router,
    public languageService: LanguageService // <---- NUEVO
  ) { }

  ngOnInit(): void {
    this.languageService.currentLang$.subscribe(l => this.lang = l);
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
        this.errorCarga = 'Error al conectar con el servidor.';
        this.cargando = false;
      }
    });
  }

  cerrarSesion(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  cambiarIdioma(): void {
    const nuevo = this.lang === 'es' ? 'en' : 'es';
    this.languageService.setLanguage(nuevo);
  }

  t(key: string) {
    return this.languageService.translate(key);
  }
}



