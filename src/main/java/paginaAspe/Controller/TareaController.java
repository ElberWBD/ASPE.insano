package paginaAspe.Controller;

import paginaAspe.Model.Tarea;
import paginaAspe.Service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tareas")
@CrossOrigin(origins = "*")
public class TareaController {
    
    @Autowired
    private TareaService tareaService;
    
    @GetMapping
    public List<Tarea> obtenerTodasLasTareas() {
        return tareaService.obtenerTodasLasTareas();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Tarea> obtenerTareaPorId(@PathVariable Long id) {
        Optional<Tarea> tarea = tareaService.obtenerTareaPorId(id);
        return tarea.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Tarea crearTarea(@RequestBody Tarea tarea) {
        return tareaService.guardarTarea(tarea);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizarTarea(@PathVariable Long id, @RequestBody Tarea tarea) {
        if (!tareaService.obtenerTareaPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        tarea.setTareaId(id);
        return ResponseEntity.ok(tareaService.guardarTarea(tarea));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long id) {
        if (!tareaService.obtenerTareaPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        tareaService.eliminarTarea(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/campaña/{campañaId}")
    public List<Tarea> obtenerTareasPorCampaña(@PathVariable Long campañaId) {
        return tareaService.obtenerTareasPorCampaña(campañaId);
    }
    
    @GetMapping("/estado/{estado}")
    public List<Tarea> obtenerTareasPorEstado(@PathVariable String estado) {
        return tareaService.obtenerTareasPorEstado(estado);
    }
    
    @GetMapping("/vencidas")
    public List<Tarea> obtenerTareasVencidas() {
        return tareaService.obtenerTareasVencidas();
    }
}