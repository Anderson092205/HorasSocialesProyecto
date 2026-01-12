package sv.gob.cementerios.cementeriosle.service;

import org.springframework.stereotype.Service;
import java.util.List;

import sv.gob.cementerios.cementeriosle.model.celda;
import sv.gob.cementerios.cementeriosle.repository.CeldaRepository;

@Service
public class CeldaService {

    private final CeldaRepository celdaRepo;

    public CeldaService(CeldaRepository celdaRepo) {
        this.celdaRepo = celdaRepo;
    }

    public List<celda> obtenerPorParcela(Long parcelaId) {
        return celdaRepo.findByParcela_Id(parcelaId);
    }
}

