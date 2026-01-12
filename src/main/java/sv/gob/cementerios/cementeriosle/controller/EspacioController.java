package sv.gob.cementerios.cementeriosle.controller;

import org.springframework.web.bind.annotation.*;
import sv.gob.cementerios.cementeriosle.model.Difunto;
import sv.gob.cementerios.cementeriosle.model.Espacio;
import sv.gob.cementerios.cementeriosle.model.Propietario;
import sv.gob.cementerios.cementeriosle.repository.EspacioRepository;
import sv.gob.cementerios.cementeriosle.service.EspacioService;

import java.util.List;

@RestController
@RequestMapping("/espacios")
@CrossOrigin(origins = "http://localhost:4200")
public class EspacioController {

    private final EspacioService espacioService;

    public EspacioController(EspacioService espacioService) {
        this.espacioService = espacioService;
    }

    // Asignar propietario
    @PostMapping("/{espacioId}/propietario/{propietarioId}")
    public Espacio asignarPropietario(
            @PathVariable Long espacioId,
            @PathVariable Long propietarioId
    ) {
        return espacioService.asignarPropietario(espacioId, propietarioId);
    }

    // Registrar difunto
    @PostMapping("/{espacioId}/difuntos")
    public Difunto registrarDifunto(
            @PathVariable Long espacioId,
            @RequestBody Difunto difunto
    ) {
        return espacioService.registrarDifunto(espacioId, difunto);
    }
}


