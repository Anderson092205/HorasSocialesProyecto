package sv.gob.cementerios.cementeriosle.model;

import jakarta.persistence.*;

@Entity
@Table(
        name = "celdas",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"parcela_id", "fila", "columna"}
        )
)
public class celda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer fila;
    private Integer columna;

    @ManyToOne
    @JoinColumn(name = "parcela_id", nullable = false)
    private Parcela parcela;




    // ===== GETTERS Y SETTERS =====

    public Long getId() {
        return id;
    }

    public Integer getFila() {
        return fila;
    }

    public void setFila(Integer fila) {
        this.fila = fila;
    }

    public Integer getColumna() {
        return columna;
    }

    public void setColumna(Integer columna) {
        this.columna = columna;
    }

    public Parcela getParcela() {
        return parcela;
    }

    public void setParcela(Parcela parcela) {
        this.parcela = parcela;
    }
}


