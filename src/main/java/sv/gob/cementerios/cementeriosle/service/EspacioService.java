package sv.gob.cementerios.cementeriosle.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sv.gob.cementerios.cementeriosle.model.Difunto;
import sv.gob.cementerios.cementeriosle.model.Espacio;
import sv.gob.cementerios.cementeriosle.model.Propietario;
import sv.gob.cementerios.cementeriosle.repository.DifuntoRepository;
import sv.gob.cementerios.cementeriosle.repository.EspacioRepository;
import sv.gob.cementerios.cementeriosle.repository.PropietarioRepository;

@Service
public class EspacioService {


    private final EspacioRepository espacioRepo;
    private final PropietarioRepository propietarioRepo;
    private final DifuntoRepository difuntoRepo;

    public EspacioService(
            EspacioRepository espacioRepo,
            PropietarioRepository propietarioRepo,
            DifuntoRepository difuntoRepo
    ) {
        this.espacioRepo = espacioRepo;
        this.propietarioRepo = propietarioRepo;
        this.difuntoRepo = difuntoRepo;
    }

    // 1️⃣ Asignar propietario
    public Espacio asignarPropietario(Long espacioId, Long propietarioId) {

        Espacio espacio = espacioRepo.findById(espacioId)
                .orElseThrow(() -> new RuntimeException("Espacio no existe"));

        Propietario propietario = propietarioRepo.findById(propietarioId)
                .orElseThrow(() -> new RuntimeException("Propietario no existe"));

        espacio.setPropietario(propietario);
        return espacioRepo.save(espacio);
    }

    // 2️⃣ Registrar difunto
    public Difunto registrarDifunto(Long espacioId, Difunto difunto) {

        Espacio espacio = espacioRepo.findById(espacioId)
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado"));

        difunto.setEspacio(espacio);


        if (espacio.getPropietario() == null) {
            throw new RuntimeException("Debe asignar propietario primero");
        }

        int cantidad = Math.toIntExact(difuntoRepo.countByEspacio_IdEspacio(espacioId));



        if (cantidad >= 4) {
            throw new RuntimeException("Espacio lleno");
        }

        difunto.setEspacio(espacio);
        return difuntoRepo.save(difunto);
    }
}


