package paginaAspe.Repository;

import paginaAspe.Model.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {
    
    // Buscar registros por usuario
    List<Auditoria> findByUsuarioId(Long usuarioId);
    
    // Buscar registros por entidad
    List<Auditoria> findByEntidad(String entidad);
    
    // Buscar registros por entidad y ID de entidad
    List<Auditoria> findByEntidadAndEntidadId(String entidad, Long entidadId);
    
    // Buscar registros por acción
    List<Auditoria> findByAccion(String accion);
    
    // Buscar registros por rango de fechas
    List<Auditoria> findByCreadoAtBetween(LocalDateTime inicio, LocalDateTime fin);
    
    // Buscar registros por usuario y entidad
    List<Auditoria> findByUsuarioIdAndEntidad(Long usuarioId, String entidad);
    
    // Buscar registros por usuario y acción
    List<Auditoria> findByUsuarioIdAndAccion(Long usuarioId, String accion);
    
    // Buscar registros recientes (ordenados por fecha descendente)
    List<Auditoria> findByOrderByCreadoAtDesc();
    
    // Buscar registros por entidad ordenados por fecha
    List<Auditoria> findByEntidadOrderByCreadoAtDesc(String entidad);
    
    // Buscar registros por usuario ordenados por fecha
    List<Auditoria> findByUsuarioIdOrderByCreadoAtDesc(Long usuarioId);
    
    // Contar registros por entidad
    Long countByEntidad(String entidad);
    
    // Contar registros por usuario
    Long countByUsuarioId(Long usuarioId);
    
    // Contar registros por acción
    Long countByAccion(String accion);
}