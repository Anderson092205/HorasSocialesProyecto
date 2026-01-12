package sv.gob.cementerios.cementeriosle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sv.gob.cementerios.cementeriosle.model.Difunto;

import java.util.List;

@Repository
public interface DifuntoRepository extends JpaRepository<Difunto, Long> {

    // ✅ Cuenta difuntos en un espacio específico
    long countByEspacio_IdEspacio(Long idEspacio);

    // ✅ Obtener difuntos por cementerio
    @Query("SELECT d FROM Difunto d WHERE d.espacio.celda.parcela.cementerio.idCementerio = :idCementerio")
    List<Difunto> findByIdCementerio(@Param("idCementerio") Integer idCementerio);
}




