package sv.gob.cementerios.cementeriosle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import sv.gob.cementerios.cementeriosle.model.Parcela;

public interface ParcelaRepository extends JpaRepository<Parcela, Long> {

    List<Parcela> findByCementerio_Id(Long cementerioId);
}



