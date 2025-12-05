package sv.gob.cementerios.cementeriosle.repository;

import sv.gob.cementerios.cementeriosle.model.AccesoCementerio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AccesoCementerioRepository extends JpaRepository<AccesoCementerio, Integer> {

    /**
     * Busca todos los registros de acceso para un usuario específico por su ID.
     */
    List<AccesoCementerio> findByUsuarioIdUsuario(Integer idUsuario);
}