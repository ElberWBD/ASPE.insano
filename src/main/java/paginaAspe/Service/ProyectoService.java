package paginaAspe.Service;

import paginaAspe.Model.Proyecto;
import paginaAspe.Repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProyectoService {
    
    @Autowired
    private ProyectoRepository proyectoRepository;
    
    // Obtener todos los proyectos
    public List<Proyecto> obtenerTodosLosProyectos() {
        return proyectoRepository.findAll();
    }
    
    // Obtener proyecto por ID
    public Optional<Proyecto> obtenerProyectoPorId(Long id) {
        return proyectoRepository.findById(id);
    }
    
    // Guardar proyecto
    public Proyecto guardarProyecto(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }
    
    // Crear nuevo proyecto
    public Proyecto crearProyecto(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }
    
    // Actualizar proyecto
    public Proyecto actualizarProyecto(Long id, Proyecto proyectoDetalles) {
        Optional<Proyecto> optionalProyecto = proyectoRepository.findById(id);
        
        if (optionalProyecto.isPresent()) {
            Proyecto proyecto = optionalProyecto.get();
            proyecto.setClienteId(proyectoDetalles.getClienteId());
            proyecto.setNombre(proyectoDetalles.getNombre());
            proyecto.setDescripcion(proyectoDetalles.getDescripcion());
            proyecto.setFechaInicio(proyectoDetalles.getFechaInicio());
            proyecto.setFechaFin(proyectoDetalles.getFechaFin());
            proyecto.setPresupuesto(proyectoDetalles.getPresupuesto());
            proyecto.setEstado(proyectoDetalles.getEstado());
            
            return proyectoRepository.save(proyecto);
        }
        return null;
    }
    
    // Eliminar proyecto
    public boolean eliminarProyecto(Long id) {
        if (proyectoRepository.existsById(id)) {
            proyectoRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Obtener proyectos por cliente
    public List<Proyecto> obtenerProyectosPorCliente(Long clienteId) {
        return proyectoRepository.findByClienteId(clienteId);
    }
    
    // Obtener proyectos por estado
    public List<Proyecto> obtenerProyectosPorEstado(String estado) {
        return proyectoRepository.findByEstado(estado);
    }
    
    // Obtener proyectos activos
    public List<Proyecto> obtenerProyectosActivos() {
        LocalDate hoy = LocalDate.now();
        return proyectoRepository.findByFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(hoy, hoy);
    }
    
    // Obtener proyectos vencidos
    public List<Proyecto> obtenerProyectosVencidos() {
        return proyectoRepository.findByFechaFinBefore(LocalDate.now());
    }
    
    // Obtener proyectos futuros
    public List<Proyecto> obtenerProyectosFuturos() {
        return proyectoRepository.findByFechaInicioAfter(LocalDate.now());
    }
    
    // Obtener proyectos por cliente y estado
    public List<Proyecto> obtenerProyectosPorClienteYEstado(Long clienteId, String estado) {
        return proyectoRepository.findByClienteIdAndEstado(clienteId, estado);
    }
    
    // Obtener proyectos recientes
    public List<Proyecto> obtenerProyectosRecientes() {
        return proyectoRepository.findByOrderByFechaInicioDesc();
    }
    
    // Buscar proyectos por nombre
    public List<Proyecto> buscarProyectosPorNombre(String nombre) {
        return proyectoRepository.findByNombreContainingIgnoreCase(nombre);
    }
    
    // Obtener proyectos por rango de presupuesto
    public List<Proyecto> obtenerProyectosPorRangoPresupuesto(BigDecimal min, BigDecimal max) {
        List<Proyecto> todos = proyectoRepository.findAll();
        return todos.stream()
                .filter(p -> p.getPresupuesto() != null)
                .filter(p -> p.getPresupuesto().compareTo(min) >= 0 && p.getPresupuesto().compareTo(max) <= 0)
                .toList();
    }
    
    // Contar proyectos por estado
    public Long contarProyectosPorEstado(String estado) {
        return proyectoRepository.countByEstado(estado);
    }
    
    // Contar proyectos por cliente
    public Long contarProyectosPorCliente(Long clienteId) {
        return proyectoRepository.countByClienteId(clienteId);
    }
    
    // Verificar si existe nombre de proyecto
    public boolean existeNombreProyecto(String nombre) {
        return proyectoRepository.existsByNombre(nombre);
    }
}