// src/app/app.ts
import { Component, signal } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { DashboardComponent } from './dashboard/dashboard.component';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    HttpClientModule,      
    RouterOutlet
  ],
  template: `
    <router-outlet></router-outlet>
  `,
  styles: []
})
export class App {
  protected readonly title = signal('cementeriosle');
}
