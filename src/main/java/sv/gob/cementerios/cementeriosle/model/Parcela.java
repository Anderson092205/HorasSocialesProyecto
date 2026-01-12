package sv.gob.cementerios.cementeriosle.model;

import jakarta.persistence.*;

@Entity
@Table(name = "parcelas")
public class Parcela {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numero;
    private Integer filas;
    private Integer columnas;

    @ManyToOne
    @JoinColumn(name = "cementerio_id", nullable = false)
    private Cementerio cementerio;

    // ===== GETTERS Y SETTERS =====

    public Long getId() {
        return id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getFilas() {
        return filas;
    }

    public void setFilas(Integer filas) {
        this.filas = filas;
    }

    public Integer getColumnas() {
        return columnas;
    }

    public void setColumnas(Integer columnas) {
        this.columnas = columnas;
    }

    public Cementerio getCementerio() {
        return cementerio;
    }

    public void setCementerio(Cementerio cementerio) {
        this.cementerio = cementerio;
    }
}


