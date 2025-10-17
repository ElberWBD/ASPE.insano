package paginaAspe.Repository;

import paginaAspe.Model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    
    // Buscar proyectos por cliente
    List<Proyecto> findByClienteId(Long clienteId);
    
    // Buscar proyectos por estado
    List<Proyecto> findByEstado(String estado);
    
    // Buscar proyectos por nombre (búsqueda parcial)
    List<Proyecto> findByNombreContainingIgnoreCase(String nombre);
    
    // Buscar proyectos por rango de fechas de inicio
    List<Proyecto> findByFechaInicioBetween(LocalDate inicio, LocalDate fin);
    
    // Buscar proyectos por rango de fechas de fin
    List<Proyecto> findByFechaFinBetween(LocalDate inicio, LocalDate fin);
    
    // Buscar proyectos activos (fecha actual entre inicio y fin)
    List<Proyecto> findByFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(LocalDate fecha1, LocalDate fecha2);
    
    // Buscar proyectos vencidos (fecha fin anterior a hoy)
    List<Proyecto> findByFechaFinBefore(LocalDate fecha);
    
    // Buscar proyectos futuros (fecha inicio posterior a hoy)
    List<Proyecto> findByFechaInicioAfter(LocalDate fecha);
    
    // Buscar proyectos por cliente y estado
    List<Proyecto> findByClienteIdAndEstado(Long clienteId, String estado);
    
    // Buscar proyectos ordenados por fecha de inicio
    List<Proyecto> findByOrderByFechaInicioDesc();
    
    // Buscar proyectos ordenados por presupuesto descendente
    List<Proyecto> findByOrderByPresupuestoDesc();
    
    // Contar proyectos por estado
    Long countByEstado(String estado);
    
    // Contar proyectos por cliente
    Long countByClienteId(Long clienteId);
    
    // Verificar si existe proyecto con nombre
    Boolean existsByNombre(String nombre);
}