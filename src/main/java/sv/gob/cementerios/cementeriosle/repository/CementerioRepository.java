package sv.gob.cementerios.cementeriosle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sv.gob.cementerios.cementeriosle.model.Cementerio;

@Repository
public interface CementerioRepository extends JpaRepository<Cementerio, Integer> {
}