package sv.gob.cementerios.cementeriosle.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "propietario")
public class Propietario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_propietario")
    private Long idPropietario;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false, unique = true)
    private String correo;

    @ManyToOne
    @JoinColumn(name = "id_cementerio", nullable = false)
    private Cementerio cementerio;

    // ======================
    // CONSTRUCTOR VACÍO
    // ======================
    public Propietario() {}

    // ======================
    // GETTERS Y SETTERS
    // ======================
    public Long getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(Long idPropietario) {
        this.idPropietario = idPropietario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Cementerio getCementerio() {
        return cementerio;
    }

    public void setCementerio(Cementerio cementerio) {
        this.cementerio = cementerio;
    }
}


