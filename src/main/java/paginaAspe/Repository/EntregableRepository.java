package paginaAspe.Repository;

import paginaAspe.Model.Entregable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EntregableRepository extends JpaRepository<Entregable, Long> {
    
    // Buscar entregables por tarea
    List<Entregable> findByTareaId(Long tareaId);
    
    // Buscar entregables por estado de aprobación
    List<Entregable> findByAprobado(Boolean aprobado);
    
    // Buscar entregables por usuario que subió
    List<Entregable> findBySubidoPor(Long subidoPor);
    
    // Buscar entregables por usuario que aprobó
    List<Entregable> findByAprobadoPor(Long aprobadoPor);
    
    // Buscar entregables por tipo MIME
    List<Entregable> findByMimeType(String mimeType);
    
    // Buscar entregables por título
    List<Entregable> findByTituloContaining(String titulo);
    
    // Buscar entregables por rango de fechas de subida
    List<Entregable> findBySubidoAtBetween(LocalDateTime inicio, LocalDateTime fin);
    
    // Buscar entregables por rango de fechas de aprobación
    List<Entregable> findByAprobadoAtBetween(LocalDateTime inicio, LocalDateTime fin);
    
    // Buscar entregables por tarea y estado de aprobación
    List<Entregable> findByTareaIdAndAprobado(Long tareaId, Boolean aprobado);
    
    // Buscar última versión de un entregable por tarea
    List<Entregable> findByTareaIdOrderByVersionDesc(Long tareaId);
    
    // Contar entregables por estado de aprobación
    Long countByAprobado(Boolean aprobado);
    
    // Contar entregables por tarea
    Long countByTareaId(Long tareaId);
    
    // Verificar si existe entregable por storageKey
    Boolean existsByStorageKey(String storageKey);
}