package paginaAspe.Repository;

import paginaAspe.Model.Campaña;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CampañaRepository extends JpaRepository<Campaña, Long> {
    
    // Buscar campañas por proyecto
    List<Campaña> findByProyectoId(Long proyectoId);
    
    // Buscar campañas por cliente
    List<Campaña> findByClienteId(Long clienteId);
    
    // Buscar campañas por estado
    List<Campaña> findByEstado(String estado);
    
    // Buscar campañas por nombre (búsqueda parcial)
    List<Campaña> findByNombreContainingIgnoreCase(String nombre);
    
    // Buscar campañas por rango de fechas de inicio
    List<Campaña> findByFechaInicioBetween(LocalDate inicio, LocalDate fin);
    
    // Buscar campañas por rango de fechas de fin
    List<Campaña> findByFechaFinBetween(LocalDate inicio, LocalDate fin);
    
    // Buscar campañas activas (fecha actual entre inicio y fin)
    List<Campaña> findByFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(LocalDate fecha1, LocalDate fecha2);
    
    // Buscar campañas vencidas (fecha fin anterior a hoy)
    List<Campaña> findByFechaFinBefore(LocalDate fecha);
    
    // Buscar campañas futuras (fecha inicio posterior a hoy)
    List<Campaña> findByFechaInicioAfter(LocalDate fecha);
    
    // Buscar campañas por proyecto y estado
    List<Campaña> findByProyectoIdAndEstado(Long proyectoId, String estado);
    
    // Buscar campañas por cliente y estado
    List<Campaña> findByClienteIdAndEstado(Long clienteId, String estado);
    
    // Buscar campañas ordenadas por fecha de inicio
    List<Campaña> findByOrderByFechaInicioDesc();
    
    // Buscar campañas ordenadas por presupuesto descendente
    List<Campaña> findByOrderByPresupuestoDesc();
    
    // Contar campañas por estado
    Long countByEstado(String estado);
    
    // Contar campañas por cliente
    Long countByClienteId(Long clienteId);
    
    // Verificar si existe campaña con nombre
    Boolean existsByNombre(String nombre);
}