package paginaAspe.Controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import paginaAspe.Model.Rol;
import paginaAspe.Model.Usuario;
import paginaAspe.Repository.RolRepository;
import paginaAspe.Repository.UsuarioRepository;
import paginaAspe.Service.UsuarioService;

@CrossOrigin("*")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    // Listar todos los usuarios
    @GetMapping("/listar")
    public List<Usuario> listar() {
        return usuarioService.listar();
    }

    // Guardar o actualizar usuario

    @PostMapping("/guardar")
    public Usuario guardar(@RequestBody Usuario usuario, @RequestParam(required = false) Long rolId) {
        // Si el usuario no tiene rol asignado, intentamos asignar uno.
        if (usuario.getRol() == null) {
            Rol rol = null;
            if (rolId != null) {
                rol = rolRepository.findById(rolId).orElse(null);
            }
            // Si no se proporcionó rol o no existe, usamos el primer rol existente como
            // defecto.
            if (rol == null) {
                rol = rolRepository.findAll().stream().findFirst().orElse(null);
            }
            usuario.setRol(rol);
        }
        return usuarioService.guardar(usuario);
    }

    // Eliminar usuario por ID
    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String email = credenciales.get("email");
        String password = credenciales.get("password");

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            // Validamos contraseña y si está activo
            if (usuario.getPassword_hash().equals(password) && usuario.getActive()) {
                return ResponseEntity.ok(Map.of(
                        "usuarioId", usuario.getUsuario_id(),
                        "username", usuario.getUsername(),
                        "email", usuario.getEmail(),
                        "rol", usuario.getRol() != null ? usuario.getRol().getNombre() : null,
                        "profesion", usuario.getProfesion(),
                        "mensaje", "Inicio de sesión exitoso"));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Correo o contraseña incorrectos"));
    }

    // Obtener datos de un usuario específico
@GetMapping("/{id}")
public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id) {
    Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
    return usuarioOpt.map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}

}
