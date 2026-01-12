package sv.gob.cementerios.cementeriosle.model;

public enum Rol {
    ADMIN,
    OPERADOR,
    VISITANTE;

    // ======================
    // MÉTODO PARA OBTENER NOMBRE
    // ======================
    public String getNombre() {
        return name(); // Devuelve el nombre del enum como String
    }
}

