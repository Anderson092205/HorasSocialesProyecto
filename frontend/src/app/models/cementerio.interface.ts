export interface Cementerio {
  id: number;
  nombre: string;
  tipo: 'Publico' | 'Privado' | 'Cenizas' | string; 
  ubicacion: string; 
  capacidad: number; 
}