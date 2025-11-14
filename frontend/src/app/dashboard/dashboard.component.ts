// src/app/dashboard/dashboard.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TestService } from '../test.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  providers: [TestService], // ← Esto habilita la inyección del servicio
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  response: any;

  constructor(private testService: TestService) {}

  ngOnInit(): void {
    this.testService.pingBackend().subscribe(data => {
      this.response = data;
    });
  }
}
