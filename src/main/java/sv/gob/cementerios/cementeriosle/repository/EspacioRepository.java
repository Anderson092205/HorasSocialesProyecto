package sv.gob.cementerios.cementeriosle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sv.gob.cementerios.cementeriosle.model.Espacio;

import java.util.List;

public interface EspacioRepository extends JpaRepository<Espacio, Long> {

    List<Espacio> findByCelda_Id(Long celdaId);
}



