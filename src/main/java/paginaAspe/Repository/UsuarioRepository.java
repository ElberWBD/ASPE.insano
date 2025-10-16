package paginaAspe.Repository;

import paginaAspe.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// === NUEVOS IMPORTS PARA @Query Y @Param ===
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    // ----------------------------------------------------------------------
    // Estos dos métodos causaban error porque no existe el campo 'ultimoAcceso'
    // en la entidad. Los conservamos pero los resolvemos con JPQL contra
    // 'updatedAt'.
    // ----------------------------------------------------------------------

    // Buscar usuarios por "último acceso" -> usamos updatedAt como proxy
    @Query("SELECT u FROM Usuario u WHERE u.updatedAt > :fecha")
    List<Usuario> findByUltimoAccesoAfter(@Param("fecha") LocalDateTime fecha);

    // Ordenar por "último acceso" -> ordenamos por updatedAt DESC
    @Query("SELECT u FROM Usuario u ORDER BY u.updatedAt DESC")
    List<Usuario> findByOrderByUltimoAccesoDesc();

    // Buscar usuarios inactivos
    List<Usuario> findByActiveFalse();

    // Buscar usuarios activos
    List<Usuario> findByActiveTrue();

    // Buscar usuarios ordenados por fecha de creación
    List<Usuario> findByOrderByCreatedAtDesc();

    // (Opcional) Si en algún punto quieres usar directamente updatedAt:
    List<Usuario> findByUpdatedAtAfter(LocalDateTime fecha);

    List<Usuario> findByOrderByUpdatedAtDesc();

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
