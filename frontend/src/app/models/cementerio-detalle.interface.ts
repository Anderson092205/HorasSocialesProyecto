// Define la estructura de Difunto que viene del backend
export interface Difunto {
  idDifunto: number;
  nombre: string;
  fechaDefuncion: Date; // O string, dependiendo de cómo manejes las fechas
  ubicacion: string;
}

// Define la estructura de Propietario
export interface Propietario {
  idPropietario: number;
  nombre: string;
  telefono: string;
  correo: string;
  totalLotes: number;
}

// Define la estructura consolidada de detalle del cementerio
export interface CementerioDetalle {
  idCementerio: number;
  nombreCementerio: string;
  tipoCementerio: string;

  // Resumen de Espacios
  totalEspacios: number;
  espaciosOcupados: number;
  espaciosDisponibles: number;

  // Listas de detalle
  difuntos: Difunto[];
  propietarios: Propietario[];
}