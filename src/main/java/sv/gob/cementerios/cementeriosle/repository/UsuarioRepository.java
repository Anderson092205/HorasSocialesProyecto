package sv.gob.cementerios.cementeriosle.repository;

import sv.gob.cementerios.cementeriosle.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // Método clave para buscar usuario por correo (username)
    Optional<Usuario> findByCorreo(String correo);
}