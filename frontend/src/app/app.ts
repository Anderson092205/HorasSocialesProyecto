// src/app/app.ts
import { Component, signal } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { DashboardComponent } from './dashboard/dashboard.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    HttpClientModule,      
    DashboardComponent
  ],
  template: `
    <app-dashboard></app-dashboard>
  `,
  styles: []
})
export class App {
  protected readonly title = signal('cementeriosle');
}
