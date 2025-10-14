package paginaAspe.Repository;

import paginaAspe.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Buscar usuario por username
    Optional<Usuario> findByUsername(String username);
    
    // Buscar usuario por email
    Optional<Usuario> findByEmail(String email);
    
    // Buscar usuarios activos
    List<Usuario> findByActive(Boolean active);
    
    // Buscar usuarios por username (búsqueda parcial)
    List<Usuario> findByUsernameContainingIgnoreCase(String username);
    
    // Buscar usuarios por email (búsqueda parcial)
    List<Usuario> findByEmailContainingIgnoreCase(String email);
    
    // Buscar usuarios por rango de fechas de creación
    List<Usuario> findByCreatedAtBetween(LocalDateTime inicio, LocalDateTime fin);
    
    // Buscar usuarios por último acceso
    List<Usuario> findByUltimoAccesoAfter(LocalDateTime fecha);
    
    // Buscar usuarios inactivos
    List<Usuario> findByActiveFalse();
    
    // Buscar usuarios activos
    List<Usuario> findByActiveTrue();
    
    // Buscar usuarios ordenados por fecha de creación
    List<Usuario> findByOrderByCreatedAtDesc();
    
    // Buscar usuarios ordenados por último acceso
    List<Usuario> findByOrderByUltimoAccesoDesc();
    
    // Verificar si existe usuario con username
    Boolean existsByUsername(String username);
    
    // Verificar si existe usuario con email
    Boolean existsByEmail(String email);
    
    // Contar usuarios activos
    Long countByActive(Boolean active);
    
    // Contar usuarios por estado
    Long countByActiveTrue();
    Long countByActiveFalse();
}