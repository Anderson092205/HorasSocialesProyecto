package sv.gob.cementerios.cementeriosle.service;

import org.springframework.stereotype.Service;
import java.util.List;

import sv.gob.cementerios.cementeriosle.model.*;
import sv.gob.cementerios.cementeriosle.repository.*;

@Service
public class ParcelaService {

    private final ParcelaRepository parcelaRepo;
    private final CeldaRepository celdaRepo;
    private final EspacioRepository espacioRepo;
    private final CementerioRepository cementerioRepo;

    public ParcelaService(
            ParcelaRepository parcelaRepo,
            CeldaRepository celdaRepo,
            EspacioRepository espacioRepo,
            CementerioRepository cementerioRepo
    ) {
        this.parcelaRepo = parcelaRepo;
        this.celdaRepo = celdaRepo;
        this.espacioRepo = espacioRepo;
        this.cementerioRepo = cementerioRepo;
    }

    // 🔹 OBTENER PARCELAS POR CEMENTERIO
    public List<Parcela> obtenerPorCementerio(Long cementerioId) {
        return parcelaRepo.findByCementerio_Id(cementerioId);
    }

    // 🔹 CREAR PARCELA CON FILAS, COLUMNAS Y ESPACIOS
    public Parcela crearParcelaConEstructura(
            Long cementerioId,
            int filas,
            int columnas,
            int espaciosPorCelda
    ) {
        Cementerio cementerio = cementerioRepo.findById(Math.toIntExact(cementerioId))
                .orElseThrow(() -> new RuntimeException("Cementerio no encontrado"));

        Parcela parcela = new Parcela();
        parcela.setCementerio(cementerio);
        parcela = parcelaRepo.save(parcela);

        for (int f = 1; f <= filas; f++) {
            for (int c = 1; c <= columnas; c++) {

                celda celda = new celda();
                celda.setFila(f);
                celda.setColumna(c);
                celda.setParcela(parcela);
                celda = celdaRepo.save(celda);

                for (int e = 1; e <= espaciosPorCelda; e++) {
                    Espacio espacio = new Espacio();
                    espacio.setNumero(e);
                    espacio.setOcupado(false);
                    espacio.setCelda(celda);
                    espacioRepo.save(espacio);
                }
            }
        }

        return parcela;
    }
}


