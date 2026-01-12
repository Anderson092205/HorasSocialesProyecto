package sv.gob.cementerios.cementeriosle.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(
        name = "espacios",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"celda_id", "numero"}
        )
)
public class Espacio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numero;
    private Boolean ocupado = false;

    @ManyToOne
    @JoinColumn(name = "celda_id", nullable = false)
    private celda celda;

    @OneToMany(mappedBy = "espacio", cascade = CascadeType.ALL)
    private List<Difunto> difuntos;

    @ManyToOne
    @JoinColumn(name = "propietario_id")
    private Propietario propietario;


    // ===== GETTERS Y SETTERS =====

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }


    public Long getId() {
        return id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Boolean getOcupado() {
        return ocupado;
    }

    public void setOcupado(Boolean ocupado) {
        this.ocupado = ocupado;
    }

    public celda getCelda() {
        return celda;
    }

    public void setCelda(celda celda) {
        this.celda = celda;
    }
}

