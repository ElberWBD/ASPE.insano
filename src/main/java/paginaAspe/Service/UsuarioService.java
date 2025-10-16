package paginaAspe.Service;

import paginaAspe.Model.Usuario;
import paginaAspe.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Obtener todos los usuarios
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    // Obtener usuario por ID
    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Obtener usuario por username
    public Optional<Usuario> obtenerUsuarioPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    // Obtener usuario por email
    public Optional<Usuario> obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Guardar usuario
    public Usuario guardarUsuario(Usuario usuario) {
        // Si es un nuevo usuario, establecer fechas
        if (usuario.getUsuarioId() == null) {
            usuario.setCreatedAt(LocalDateTime.now());
        }
        usuario.setUpdatedAt(LocalDateTime.now());
        return usuarioRepository.save(usuario);
    }

    // Crear nuevo usuario
    public Usuario crearUsuario(Usuario usuario) {
        usuario.setCreatedAt(LocalDateTime.now());
        usuario.setUpdatedAt(LocalDateTime.now());
        if (usuario.getActive() == null) {
            usuario.setActive(true);
        }
        return usuarioRepository.save(usuario);
    }

    // Actualizar usuario
    public Usuario actualizarUsuario(Long id, Usuario usuarioDetalles) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            usuario.setUsername(usuarioDetalles.getUsername());
            usuario.setEmail(usuarioDetalles.getEmail());
            usuario.setPasswordHash(usuarioDetalles.getPasswordHash());
            usuario.setActive(usuarioDetalles.getActive());
            usuario.setUpdatedAt(LocalDateTime.now());

            return usuarioRepository.save(usuario);
        }
        return null;
    }

    // Eliminar usuario
    public boolean eliminarUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Actualizar "último acceso" sin cambiar la entidad: usamos updatedAt
    public Usuario actualizarUltimoAcceso(Long id) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            // Parche: en vez de setUltimoAcceso(...), usamos updatedAt que ya existe
            usuario.setUpdatedAt(LocalDateTime.now());
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    // Activar/desactivar usuario
    public Usuario cambiarEstadoUsuario(Long id, Boolean activo) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            usuario.setActive(activo);
            usuario.setUpdatedAt(LocalDateTime.now());
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    // Obtener usuarios activos
    public List<Usuario> obtenerUsuariosActivos() {
        return usuarioRepository.findByActiveTrue();
    }

    // Obtener usuarios inactivos
    public List<Usuario> obtenerUsuariosInactivos() {
        return usuarioRepository.findByActiveFalse();
    }

    // Buscar usuarios por username
    public List<Usuario> buscarUsuariosPorUsername(String username) {
        return usuarioRepository.findByUsernameContainingIgnoreCase(username);
    }

    // Buscar usuarios por email
    public List<Usuario> buscarUsuariosPorEmail(String email) {
        return usuarioRepository.findByEmailContainingIgnoreCase(email);
    }

    // Obtener usuarios recientes
    public List<Usuario> obtenerUsuariosRecientes() {
        return usuarioRepository.findByOrderByCreatedAtDesc();
    }

    // Verificar si existe username
    public boolean existeUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    // Verificar si existe email
    public boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    // Contar usuarios activos
    public Long contarUsuariosActivos() {
        return usuarioRepository.countByActiveTrue();
    }

    // Contar usuarios inactivos
    public Long contarUsuariosInactivos() {
        return usuarioRepository.countByActiveFalse();
    }
}