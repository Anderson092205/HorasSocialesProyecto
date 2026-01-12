package sv.gob.cementerios.cementeriosle.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "historial_espacio")
public class HistorialEspacio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHistorial;

    @ManyToOne
    @JoinColumn(name = "id_espacio", nullable = false)
    private Espacio espacio;

    @Column(nullable = false)
    private String propietario;

    @Column(nullable = false)
    private String difunto;

    @Column(nullable = false)
    private LocalDate fechaIngreso;

    @Column
    private LocalDate fechaSalida;

    // ======================
    // CONSTRUCTORES
    // ======================
    public HistorialEspacio() {
    }

    // ======================
    // GETTERS Y SETTERS
    // ======================

    public Long getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(Long idHistorial) {
        this.idHistorial = idHistorial;
    }

    public Espacio getEspacio() {
        return espacio;
    }

    public void setEspacio(Espacio espacio) {
        this.espacio = espacio;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getDifunto() {
        return difunto;
    }

    public void setDifunto(String difunto) {
        this.difunto = difunto;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }
}


