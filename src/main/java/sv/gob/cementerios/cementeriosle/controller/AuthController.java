package sv.gob.cementerios.cementeriosle.controller;

import sv.gob.cementerios.cementeriosle.dto.AuthRequest;
import sv.gob.cementerios.cementeriosle.dto.AuthResponse;
import sv.gob.cementerios.cementeriosle.security.JwtUtil;
import sv.gob.cementerios.cementeriosle.model.Usuario;
import sv.gob.cementerios.cementeriosle.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import sv.gob.cementerios.cementeriosle.service.UsuarioService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UsuarioService service;

    @PostMapping("/login")
    public Usuario login(@RequestBody Map<String, String> body) {
        return service.validar(
                body.get("username"),
                body.get("password")
        );
    }
}
