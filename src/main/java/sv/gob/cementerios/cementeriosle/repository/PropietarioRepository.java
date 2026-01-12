package sv.gob.cementerios.cementeriosle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sv.gob.cementerios.cementeriosle.model.Propietario;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropietarioRepository extends JpaRepository<Propietario, Long> {

    // ✅ Buscar propietario por correo (útil para validaciones de login o duplicados)
    Optional<Propietario> findByCorreo(String correo);

    // ✅ Obtener todos los propietarios asociados a un cementerio específico
    @Query("SELECT p FROM Propietario p WHERE p.cementerio.idCementerio = :idCementerio")
    List<Propietario> findPropietariosByCementerioId(@Param("idCementerio") Integer idCementerio);
}


