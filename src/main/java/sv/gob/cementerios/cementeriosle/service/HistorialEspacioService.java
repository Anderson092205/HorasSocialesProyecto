package sv.gob.cementerios.cementeriosle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sv.gob.cementerios.cementeriosle.model.Espacio;
import sv.gob.cementerios.cementeriosle.model.HistorialEspacio;
import sv.gob.cementerios.cementeriosle.repository.EspacioRepository;
import sv.gob.cementerios.cementeriosle.repository.HistorialEspacioRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class HistorialEspacioService {

    @Autowired
    private HistorialEspacioRepository repo;

    @Autowired
    private EspacioRepository espacioRepo;

    public HistorialEspacio registrar(Long espacioId, String propietario, String difunto) {

        Espacio espacio = espacioRepo.findById(espacioId)
                .orElseThrow();

        HistorialEspacio h = new HistorialEspacio();
        h.setEspacio(espacio);
        h.setPropietario(propietario);
        h.setDifunto(difunto);
        h.setFechaIngreso(LocalDate.now());

        espacio.setOcupado(true);
        espacioRepo.save(espacio);

        return repo.save(h);
    }

    public List<HistorialEspacio> obtenerHistorial(Long espacioId) {
        return repo.findByEspacioIdEspacio(espacioId);
    }

    public void liberarEspacio(Long historialId) {
        HistorialEspacio h = repo.findById(historialId).orElseThrow();
        h.setFechaSalida(LocalDate.now());

        Espacio espacio = h.getEspacio();
        espacio.setOcupado(false);

        repo.save(h);
        espacioRepo.save(espacio);
    }
}

