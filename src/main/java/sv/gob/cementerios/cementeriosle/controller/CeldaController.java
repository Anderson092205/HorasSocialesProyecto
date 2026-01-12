package sv.gob.cementerios.cementeriosle.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;

import sv.gob.cementerios.cementeriosle.model.celda;
import sv.gob.cementerios.cementeriosle.service.CeldaService;

@RestController
@RequestMapping("/celdas")
@CrossOrigin(origins = "http://localhost:4200")
public class CeldaController {

    private final CeldaService celdaService;

    public CeldaController(CeldaService celdaService) {
        this.celdaService = celdaService;
    }

    @GetMapping("/parcela/{parcelaId}")
    public List<celda> obtenerPorParcela(@PathVariable Long parcelaId) {
        return celdaService.obtenerPorParcela(parcelaId);
    }
}




