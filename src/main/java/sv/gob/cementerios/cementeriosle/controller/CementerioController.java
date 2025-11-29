package sv.gob.cementerios.cementeriosle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sv.gob.cementerios.cementeriosle.dto.CementerioDetalleDTO;
import sv.gob.cementerios.cementeriosle.dto.CementerioResponseDTO;
import sv.gob.cementerios.cementeriosle.service.CementerioService;

import java.util.List;

// 1. ANOTACIÓN BASE: Define el inicio de la URL
@RestController
@RequestMapping("/api/v1/cementerios") // <--- ¡AQUÍ ESTÁ LA RUTA BASE!
public class CementerioController {

    @Autowired
    private CementerioService cementerioService;

    // 2. ENDPOINT DE LISTADO (GET /api/v1/cementerios)
    @GetMapping
    public ResponseEntity<List<CementerioResponseDTO>> obtenerTodosLosCementerios() {
        // Llama al servicio que ya codificamos (CementerioServiceImpl)
        List<CementerioResponseDTO> cementerios = cementerioService.obtenerTodosLosCementerios();
        return ResponseEntity.ok(cementerios);
    }

    // 3. ENDPOINT DE DETALLE (GET /api/v1/cementerios/{id})
    @GetMapping("/{id}")
    public ResponseEntity<CementerioDetalleDTO> obtenerDetalleCementerio(@PathVariable Integer id) {
        CementerioDetalleDTO detalle = cementerioService.obtenerDetallePorId(id);
        return ResponseEntity.ok(detalle);
    }
}