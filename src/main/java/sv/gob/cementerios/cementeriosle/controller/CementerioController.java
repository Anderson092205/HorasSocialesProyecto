package sv.gob.cementerios.cementeriosle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sv.gob.cementerios.cementeriosle.dto.CementerioDetalleDTO;
import sv.gob.cementerios.cementeriosle.dto.CementerioResponseDTO;
import sv.gob.cementerios.cementeriosle.service.CementerioService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cementerios") // Ruta base
@CrossOrigin(origins = "http://localhost:4200") // CORS Habilitado
public class CementerioController {

    @Autowired
    private CementerioService cementerioService;

    // ==========================================================
    // 1. ENDPOINT DE LISTADO SEGURO (Manejo de Permisos)
    //    Ruta: GET /api/v1/cementerios?usuarioId=X&rol=Y
    // ==========================================================
    @GetMapping
    public ResponseEntity<List<CementerioResponseDTO>> obtenerCementeriosPorUsuario(
            // El frontend Angular debe enviar estos dos parámetros después del login
            @RequestParam Integer usuarioId,
            @RequestParam String rol) {

        // Llama al servicio, que internamente filtra por ADMIN o por tabla de accesos
        List<CementerioResponseDTO> cementerios =
                cementerioService.obtenerCementeriosPorUsuario(usuarioId, rol);

        if (cementerios.isEmpty()) {
            // Devuelve 200 OK con una lista vacía si no hay resultados, lo que Angular interpreta
            return ResponseEntity.ok(cementerios);
        }

        return ResponseEntity.ok(cementerios);
    }

    // ==========================================================
    // 2. ENDPOINT DE DETALLE
    //    Ruta: GET /api/v1/cementerios/{id}
    // ==========================================================
    @GetMapping("/{id}")
    public ResponseEntity<CementerioDetalleDTO> obtenerDetalleCementerio(@PathVariable Integer id) {
        // Llama al servicio para obtener el detalle consolidado
        CementerioDetalleDTO detalle = cementerioService.obtenerDetallePorId(id);
        return ResponseEntity.ok(detalle);
    }
}