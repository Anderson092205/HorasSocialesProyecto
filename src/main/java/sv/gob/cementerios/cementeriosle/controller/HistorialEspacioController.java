package sv.gob.cementerios.cementeriosle.controller;

import sv.gob.cementerios.cementeriosle.model.HistorialEspacio;
import sv.gob.cementerios.cementeriosle.service.HistorialEspacioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/historial")
@CrossOrigin
public class HistorialEspacioController {

    @Autowired
    private HistorialEspacioService service;

    @PostMapping("/{espacioId}")
    public void registrar(
            @PathVariable Long espacioId,
            @RequestBody Map<String, String> body
    ) {
        service.registrar(
                espacioId,
                body.get("propietario"),
                body.get("difunto")
        );
    }

    @GetMapping("/{espacioId}")
    public List<HistorialEspacio> historial(
            @PathVariable Long espacioId
    ) {
        return service.obtenerHistorial(espacioId);
    }

    @PutMapping("/liberar/{historialId}")
    public void liberar(
            @PathVariable Long historialId
    ) {
        service.liberarEspacio(historialId);
    }
}



