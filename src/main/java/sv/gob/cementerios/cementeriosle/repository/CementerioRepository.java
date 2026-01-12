package sv.gob.cementerios.cementeriosle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sv.gob.cementerios.cementeriosle.model.Cementerio;

@Repository
public interface CementerioRepository extends JpaRepository<Cementerio, Integer> {

    // ✅ Total de espacios por cementerio
    @Query("SELECT COUNT(e) FROM Espacio e WHERE e.celda.parcela.cementerio.idCementerio = :idCementerio")
    Long contarTotalEspaciosPorCementerio(@Param("idCementerio") Integer idCementerio);

    // ✅ Espacios ocupados por cementerio
    @Query("SELECT COUNT(e) FROM Espacio e WHERE e.ocupado = true AND e.celda.parcela.cementerio.idCementerio = :idCementerio")
    Long contarEspaciosOcupadosPorCementerio(@Param("idCementerio") Integer idCementerio);
}



