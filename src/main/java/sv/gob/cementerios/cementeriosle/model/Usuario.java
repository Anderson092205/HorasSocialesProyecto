package sv.gob.cementerios.cementeriosle.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(nullable = false, unique = true)
    private String correo;   // 🔄 antes era username

    @Column(nullable = false)
    private String contrasena;   // 🔄 antes era password

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    // ======================
    // CONSTRUCTOR VACÍO
    // ======================
    public Usuario() {
    }

    // ======================
    // GETTERS Y SETTERS
    // ======================

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    /**
     * ⚠️ Nota de seguridad:
     * Usa BCrypt para encriptar contraseñas antes de guardarlas.
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}

