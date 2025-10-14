package paginaAspe.Repository;

import paginaAspe.Model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
    
    // Buscar tareas por campaña
    List<Tarea> findByCampañaId(Long campañaId);
    
    // Buscar tareas por estado
    List<Tarea> findByEstado(String estado);
    
    // Buscar tareas por prioridad
    List<Tarea> findByPrioridad(Integer prioridad);
    
    // Buscar tareas vencidas
    List<Tarea> findByFechaVencimientoBefore(LocalDate fecha);
    
    // Buscar tareas por título
    List<Tarea> findByTituloContaining(String titulo);
    
    // Buscar tareas entre fechas de vencimiento
    List<Tarea> findByFechaVencimientoBetween(LocalDate inicio, LocalDate fin);
    
    // Buscar tareas por estado y prioridad
    List<Tarea> findByEstadoAndPrioridad(String estado, Integer prioridad);
    
    // Buscar tareas por campaña y estado
    List<Tarea> findByCampañaIdAndEstado(Long campañaId, String estado);
    
    // Contar tareas por estado
    Long countByEstado(String estado);
}