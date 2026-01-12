package sv.gob.cementerios.cementeriosle.repository;

import org.springframework.stereotype.Repository;
import sv.gob.cementerios.cementeriosle.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
}


