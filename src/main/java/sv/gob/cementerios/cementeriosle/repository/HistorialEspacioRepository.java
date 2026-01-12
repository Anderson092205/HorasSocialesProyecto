package sv.gob.cementerios.cementeriosle.repository;

import sv.gob.cementerios.cementeriosle.model.HistorialEspacio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistorialEspacioRepository
        extends JpaRepository<HistorialEspacio, Long> {

    List<HistorialEspacio> findByEspacioIdEspacio(Long espacioId);
}


