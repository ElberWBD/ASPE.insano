package paginaAspe.Repository;

import paginaAspe.Model.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    
    // Buscar reportes por campaña
    List<Reporte> findByCampañaId(Long campañaId);
    
    // Buscar reportes por usuario que los generó
    List<Reporte> findByGeneradoPor(Long generadoPor);
    
    // Buscar reportes por tipo
    List<Reporte> findByTipo(String tipo);
    
    // Buscar reportes por rango de fechas de generación
    List<Reporte> findByGeneradoAtBetween(LocalDateTime inicio, LocalDateTime fin);
    
    // Buscar reportes por campaña y tipo
    List<Reporte> findByCampañaIdAndTipo(Long campañaId, String tipo);
    
    // Buscar reportes por usuario y tipo
    List<Reporte> findByGeneradoPorAndTipo(Long generadoPor, String tipo);
    
    // Buscar reportes recientes (ordenados por fecha descendente)
    List<Reporte> findByOrderByGeneradoAtDesc();
    
    // Buscar reportes por campaña ordenados por fecha
    List<Reporte> findByCampañaIdOrderByGeneradoAtDesc(Long campañaId);
    
    // Buscar reportes por storageKey
    Optional<Reporte> findByStorageKey(String storageKey);
    
    // Contar reportes por tipo
    Long countByTipo(String tipo);
    
    // Contar reportes por campaña
    Long countByCampañaId(Long campañaId);
    
    // Verificar si existe reporte por storageKey
    Boolean existsByStorageKey(String storageKey);
}