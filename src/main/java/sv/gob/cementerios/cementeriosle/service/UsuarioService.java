package sv.gob.cementerios.cementeriosle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sv.gob.cementerios.cementeriosle.model.Usuario;
import sv.gob.cementerios.cementeriosle.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repo;

    public Usuario validar(String correo, String contrasena) {
        Usuario u = repo.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!u.getContrasena().equals(contrasena)) {
            throw new RuntimeException("Credenciales inválidas");
        }

        return u;
    }
}


