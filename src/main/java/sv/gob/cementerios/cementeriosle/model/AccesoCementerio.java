package sv.gob.cementerios.cementeriosle.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "acceso_cementerio")
public class AccesoCementerio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_acceso")
    private Long idAcceso;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_cementerio", nullable = false)
    private Cementerio cementerio;

    @Column(name = "puede_ver", nullable = false)
    private boolean puedeVer;

    // ======================
    // CONSTRUCTOR VACÍO
    // ======================
    public AccesoCementerio() {}

    // ======================
    // GETTERS Y SETTERS
    // ======================
    public Long getIdAcceso() {
        return idAcceso;
    }

    public void setIdAcceso(Long idAcceso) {
        this.idAcceso = idAcceso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cementerio getCementerio() {
        return cementerio;
    }

    public void setCementerio(Cementerio cementerio) {
        this.cementerio = cementerio;
    }

    public boolean getPuedeVer() {
        return puedeVer;
    }

    public void setPuedeVer(boolean puedeVer) {
        this.puedeVer = puedeVer;
    }
}
