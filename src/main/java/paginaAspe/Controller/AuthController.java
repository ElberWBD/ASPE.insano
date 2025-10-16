package paginaAspe.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paginaAspe.Model.Rol;
import paginaAspe.Model.Usuario;
import paginaAspe.Repository.RolRepository;
import paginaAspe.Repository.UsuarioRepository;
import paginaAspe.Security.JwtUtil;
import paginaAspe.Service.PasswordService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UsuarioRepository usuarios;
    private final RolRepository roles;
    private final PasswordService passwords;
    private final JwtUtil jwt;

    public AuthController(UsuarioRepository usuarios, RolRepository roles,
            PasswordService passwords, JwtUtil jwt) {
        this.usuarios = usuarios;
        this.roles = roles;
        this.passwords = passwords;
        this.jwt = jwt;
    }

    // Registro SOLO CLIENTE
    @PostMapping("/registro")
    public ResponseEntity<?> registro(@RequestBody Map<String, String> body) {
        String username = body.getOrDefault("username", "").trim();
        String email = body.getOrDefault("email", "").trim();
        String password = body.getOrDefault("password", "").trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty())
            return ResponseEntity.badRequest().body(Map.of("error", "Campos requeridos"));

        if (usuarios.findByEmail(email).isPresent())
            return ResponseEntity.badRequest().body(Map.of("error", "Email ya registrado"));

        Rol rolCliente = roles.findByNombre("CLIENTE").orElseThrow();
        Usuario u = new Usuario();
        u.setUsername(username);
        u.setEmail(email);
        u.setPasswordHash(passwords.hash(password));
        u.setRol(rolCliente);
        u.setActive(true);
        usuarios.save(u);

        String token = jwt.generate(Map.of("id", u.getUsuarioId(), "email", u.getEmail(), "rol", "CLIENTE"));
        return ResponseEntity.ok(Map.of("token", token,
                "usuario",
                Map.of("id", u.getUsuarioId(), "username", u.getUsername(), "email", u.getEmail(), "rol", "CLIENTE")));
    }

    // Login CLIENTE
    @PostMapping("/login/cliente")
    public ResponseEntity<?> loginCliente(@RequestBody Map<String, String> body) {
        return loginGenerico(body, "CLIENTE");
    }

    // Login ADMIN (sin registro)
    @PostMapping("/login/admin")
    public ResponseEntity<?> loginAdmin(@RequestBody Map<String, String> body) {
        return loginGenerico(body, "ADMIN");
    }

    private ResponseEntity<?> loginGenerico(Map<String, String> body, String rolEsperado) {
        String email = body.getOrDefault("email", "").trim();
        String password = body.getOrDefault("password", "").trim();

        Usuario u = usuarios.findByEmail(email).orElse(null);
        if (u == null || !Boolean.TRUE.equals(u.getActive()) || u.getRol() == null
                || !rolEsperado.equalsIgnoreCase(u.getRol().getNombre()))
            return ResponseEntity.status(401).body(Map.of("error", "Credenciales inválidas"));

        if (!passwords.matches(password, u.getPasswordHash()))
            return ResponseEntity.status(401).body(Map.of("error", "Credenciales inválidas"));

        String token = jwt.generate(Map.of("id", u.getUsuarioId(), "email", u.getEmail(), "rol", rolEsperado));
        return ResponseEntity.ok(Map.of("token", token,
                "usuario", Map.of("id", u.getUsuarioId(), "username", u.getUsername(), "email", u.getEmail(), "rol",
                        rolEsperado)));
    }
}
