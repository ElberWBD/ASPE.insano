package paginaAspe.Service;

import paginaAspe.Model.Entregable;
import paginaAspe.Repository.EntregableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EntregableService {
    
    @Autowired
    private EntregableRepository entregableRepository;
    
    // Obtener todos los entregables
    public List<Entregable> obtenerTodosLosEntregables() {
        return entregableRepository.findAll();
    }
    
    // Obtener entregable por ID
    public Optional<Entregable> obtenerEntregablePorId(Long id) {
        return entregableRepository.findById(id);
    }
    
    // Guardar entregable
    public Entregable guardarEntregable(Entregable entregable) {
        return entregableRepository.save(entregable);
    }
    
    // Eliminar entregable
    public void eliminarEntregable(Long id) {
        entregableRepository.deleteById(id);
    }
    
    // Obtener entregables por tarea
    public List<Entregable> obtenerEntregablesPorTarea(Long tareaId) {
        return entregableRepository.findByTareaId(tareaId);
    }
    
    // Obtener entregables por estado de aprobación
    public List<Entregable> obtenerEntregablesPorAprobacion(Boolean aprobado) {
        return entregableRepository.findByAprobado(aprobado);
    }
    
    // Obtener entregables por usuario que subió
    public List<Entregable> obtenerEntregablesPorSubidoPor(Long usuarioId) {
        return entregableRepository.findBySubidoPor(usuarioId);
    }
    
    // Obtener entregables aprobados por usuario
    public List<Entregable> obtenerEntregablesPorAprobadoPor(Long usuarioId) {
        return entregableRepository.findByAprobadoPor(usuarioId);
    }
    
    // Obtener entregables por tipo MIME
    public List<Entregable> obtenerEntregablesPorTipoMIME(String mimeType) {
        return entregableRepository.findByMimeType(mimeType);
    }
    
    // Obtener últimas versiones por tarea
    public List<Entregable> obtenerUltimasVersionesPorTarea(Long tareaId) {
        return entregableRepository.findByTareaIdOrderByVersionDesc(tareaId);
    }
    
    // Obtener entregables por tarea y aprobación
    public List<Entregable> obtenerEntregablesPorTareaYAprobacion(Long tareaId, Boolean aprobado) {
        return entregableRepository.findByTareaIdAndAprobado(tareaId, aprobado);
    }
    
    // Aprobar entregable
    public Entregable aprobarEntregable(Long entregableId, Long usuarioId) {
        Optional<Entregable> optionalEntregable = entregableRepository.findById(entregableId);
        if (optionalEntregable.isPresent()) {
            Entregable entregable = optionalEntregable.get();
            entregable.setAprobado(true);
            entregable.setAprobadoPor(usuarioId);
            entregable.setAprobadoAt(LocalDateTime.now());
            return entregableRepository.save(entregable);
        }
        return null;
    }
    
    // Contar entregables por estado
    public Long contarEntregablesPorAprobacion(Boolean aprobado) {
        return entregableRepository.countByAprobado(aprobado);
    }
    
    // Contar entregables por tarea
    public Long contarEntregablesPorTarea(Long tareaId) {
        return entregableRepository.countByTareaId(tareaId);
    }
}