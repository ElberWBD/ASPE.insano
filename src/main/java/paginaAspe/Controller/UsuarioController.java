package paginaAspe.Controller;

import paginaAspe.Model.Usuario;
import paginaAspe.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioService.obtenerTodosLosUsuarios();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.obtenerUsuarioPorId(id);
        return usuario.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/username/{username}")
    public ResponseEntity<Usuario> obtenerUsuarioPorUsername(@PathVariable String username) {
        Optional<Usuario> usuario = usuarioService.obtenerUsuarioPorUsername(username);
        return usuario.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> obtenerUsuarioPorEmail(@PathVariable String email) {
        Optional<Usuario> usuario = usuarioService.obtenerUsuarioPorEmail(email);
        return usuario.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return usuarioService.crearUsuario(usuario);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario usuarioActualizado = usuarioService.actualizarUsuario(id, usuario);
        if (usuarioActualizado != null) {
            return ResponseEntity.ok(usuarioActualizado);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        boolean eliminado = usuarioService.eliminarUsuario(id);
        if (eliminado) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @PutMapping("/{id}/ultimo-acceso")
    public ResponseEntity<Usuario> actualizarUltimoAcceso(@PathVariable Long id) {
        Usuario usuario = usuarioService.actualizarUltimoAcceso(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PutMapping("/{id}/estado/{activo}")
    public ResponseEntity<Usuario> cambiarEstadoUsuario(@PathVariable Long id, @PathVariable Boolean activo) {
        Usuario usuario = usuarioService.cambiarEstadoUsuario(id, activo);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario datosLogin) {
    Optional<Usuario> usuarioOpt = usuarioService.obtenerUsuarioPorEmail(datosLogin.getEmail());

    if (usuarioOpt.isEmpty()) {
        return ResponseEntity.status(404).body("Usuario no encontrado");
    }

    Usuario usuario = usuarioOpt.get();

    if (!usuario.getPasswordHash().equals(datosLogin.getPasswordHash())) {
        return ResponseEntity.status(401).body("Contraseña incorrecta");
    }

    usuarioService.actualizarUltimoAcceso(usuario.getUsuarioId());

    usuario.setPasswordHash(null);
    return ResponseEntity.ok(usuario);
}

    
    @GetMapping("/activos")
    public List<Usuario> obtenerUsuariosActivos() {
        return usuarioService.obtenerUsuariosActivos();
    }
    
    @GetMapping("/inactivos")
    public List<Usuario> obtenerUsuariosInactivos() {
        return usuarioService.obtenerUsuariosInactivos();
    }
    
    @GetMapping("/buscar/username")
    public List<Usuario> buscarUsuariosPorUsername(@RequestParam String username) {
        return usuarioService.buscarUsuariosPorUsername(username);
    }
    
    @GetMapping("/buscar/email")
    public List<Usuario> buscarUsuariosPorEmail(@RequestParam String email) {
        return usuarioService.buscarUsuariosPorEmail(email);
    }
    
    @GetMapping("/recientes")
    public List<Usuario> obtenerUsuariosRecientes() {
        return usuarioService.obtenerUsuariosRecientes();
    }
    
    @GetMapping("/verificar/username/{username}")
    public boolean verificarUsername(@PathVariable String username) {
        return usuarioService.existeUsername(username);
    }
    
    @GetMapping("/verificar/email/{email}")
    public boolean verificarEmail(@PathVariable String email) {
        return usuarioService.existeEmail(email);
    }
    
    @GetMapping("/estadisticas/activos")
    public Long contarUsuariosActivos() {
        return usuarioService.contarUsuariosActivos();
    }
    
    @GetMapping("/estadisticas/inactivos")
    public Long contarUsuariosInactivos() {
        return usuarioService.contarUsuariosInactivos();
    }
}