package sv.gob.cementerios.cementeriosle.controller;

import sv.gob.cementerios.cementeriosle.dto.AuthRequest;
import sv.gob.cementerios.cementeriosle.dto.AuthResponse;
import sv.gob.cementerios.cementeriosle.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
// Permite llamadas desde Angular (puerto 4200 por defecto)
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) {
        try {
            // 1. Intenta autenticar al usuario
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getCorreo(), authRequest.getPassword())
            );

            // 2. Si es exitoso, obtiene el usuario de seguridad
            final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // 3. Genera el token JWT
            final String jwt = jwtUtil.generateToken(userDetails);

            // 4. Devuelve el token en la respuesta
            return ResponseEntity.ok(new AuthResponse(jwt));

        } catch (BadCredentialsException e) {
            // Manejo de credenciales incorrectas (401)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales de usuario o contraseña incorrectas.");
        }
    }
}