package sv.gob.cementerios.cementeriosle.dto;

import lombok.Getter;

public class AuthResponse {

    // Getters
    @Getter
    private final String token;
    @Getter
    private final Integer id;
    @Getter
    private final String rol;
    @Getter
    private final Boolean esTemporal;

    // Constructor que usa todos los campos
    public AuthResponse(String token, Integer id, String rol,  Boolean esTemporal) {
        this.token = token;
        this.id = id;
        this.rol = rol;
        this .esTemporal = esTemporal;
    }

}