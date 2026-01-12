package sv.gob.cementerios.cementeriosle.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;

import sv.gob.cementerios.cementeriosle.model.Parcela;
import sv.gob.cementerios.cementeriosle.service.ParcelaService;

@RestController
@RequestMapping("/parcelas")
@CrossOrigin(origins = "http://localhost:4200")
public class ParcelaController {

    private final ParcelaService parcelaService;

    public ParcelaController(ParcelaService parcelaService) {
        this.parcelaService = parcelaService;
    }

    // 🔹 OBTENER PARCELAS POR CEMENTERIO
    @GetMapping("/cementerio/{cementerioId}")
    public List<Parcela> obtenerPorCementerio(@PathVariable Long cementerioId) {
        return parcelaService.obtenerPorCementerio(cementerioId);
    }

    // 🔹 CREAR PARCELA CON ESTRUCTURA
    @PostMapping("/crear")
    public Parcela crearParcela(
            @RequestParam Long cementerioId,
            @RequestParam int filas,
            @RequestParam int columnas,
            @RequestParam int espacios
    ) {
        return parcelaService.crearParcelaConEstructura(
                cementerioId,
                filas,
                columnas,
                espacios
        );
    }
}


