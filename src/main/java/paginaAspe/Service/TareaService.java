package paginaAspe.Service;

import paginaAspe.Model.Tarea;
import paginaAspe.Repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TareaService {
    
    @Autowired
    private TareaRepository tareaRepository;
    
    // Obtener todas las tareas
    public List<Tarea> obtenerTodasLasTareas() {
        return tareaRepository.findAll();
    }
    
    // Obtener tarea por ID
    public Optional<Tarea> obtenerTareaPorId(Long id) {
        return tareaRepository.findById(id);
    }
    
    // Guardar tarea (crear o actualizar)
    public Tarea guardarTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }
    
    // Eliminar tarea
    public void eliminarTarea(Long id) {
        tareaRepository.deleteById(id);
    }
    
    // Obtener tareas por campaña
    public List<Tarea> obtenerTareasPorCampaña(Long campañaId) {
        return tareaRepository.findByCampañaId(campañaId);
    }
    
    // Obtener tareas por estado
    public List<Tarea> obtenerTareasPorEstado(String estado) {
        return tareaRepository.findByEstado(estado);
    }
    
    // Obtener tareas vencidas
    public List<Tarea> obtenerTareasVencidas() {
        return tareaRepository.findByFechaVencimientoBefore(LocalDate.now());
    }
    
    // Obtener tareas por prioridad
    public List<Tarea> obtenerTareasPorPrioridad(Integer prioridad) {
        return tareaRepository.findByPrioridad(prioridad);
    }
    
    // Obtener tareas por campaña y estado
    public List<Tarea> obtenerTareasPorCampañaYEstado(Long campañaId, String estado) {
        return tareaRepository.findByCampañaIdAndEstado(campañaId, estado);
    }
    
    // Contar tareas por estado
    public Long contarTareasPorEstado(String estado) {
        return tareaRepository.countByEstado(estado);
    }
}