package sv.gob.cementerios.cementeriosle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sv.gob.cementerios.cementeriosle.model.celda;

import java.util.List;

public interface CeldaRepository extends JpaRepository<celda, Long> {

    List<celda> findByParcela_Id(Long parcelaId);

}

