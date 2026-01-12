package sv.gob.cementerios.cementeriosle.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "difunto")
public class Difunto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_difunto") // 🔑 nombre de columna más claro
    private Long idDifunto;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "fecha_defuncion", nullable = false)
    private LocalDate fechaDefuncion;

    @ManyToOne
    @JoinColumn(name = "espacio_id", nullable = false)
    private Espacio espacio;


    // ======================
    // CONSTRUCTOR VACÍO
    // ======================
    public Difunto() {}

    // ======================
    // GETTERS Y SETTERS
    // ======================
    public Long getIdDifunto() {
        return idDifunto;
    }

    public void setIdDifunto(Long idDifunto) {
        this.idDifunto = idDifunto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaDefuncion() {
        return fechaDefuncion;
    }

    public void setFechaDefuncion(LocalDate fechaDefuncion) {
        this.fechaDefuncion = fechaDefuncion;
    }

    public Espacio getEspacio() {
        return espacio;
    }

    public void setEspacio(Espacio espacio) {
        this.espacio = espacio;
    }
}

