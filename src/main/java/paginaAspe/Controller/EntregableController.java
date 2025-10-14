package paginaAspe.Controller;

import paginaAspe.Model.Entregable;
import paginaAspe.Service.EntregableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/entregables")
@CrossOrigin(origins = "*")
public class EntregableController {
    
    @Autowired
    private EntregableService entregableService;
    
    @GetMapping
    public List<Entregable> obtenerTodosLosEntregables() {
        return entregableService.obtenerTodosLosEntregables();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Entregable> obtenerEntregablePorId(@PathVariable Long id) {
        Optional<Entregable> entregable = entregableService.obtenerEntregablePorId(id);
        return entregable.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Entregable crearEntregable(@RequestBody Entregable entregable) {
        return entregableService.guardarEntregable(entregable);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Entregable> actualizarEntregable(@PathVariable Long id, @RequestBody Entregable entregable) {
        if (!entregableService.obtenerEntregablePorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        entregable.setEntregableId(id);
        return ResponseEntity.ok(entregableService.guardarEntregable(entregable));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEntregable(@PathVariable Long id) {
        if (!entregableService.obtenerEntregablePorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        entregableService.eliminarEntregable(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/tarea/{tareaId}")
    public List<Entregable> obtenerEntregablesPorTarea(@PathVariable Long tareaId) {
        return entregableService.obtenerEntregablesPorTarea(tareaId);
    }
    
    @GetMapping("/aprobados/{aprobado}")
    public List<Entregable> obtenerEntregablesPorAprobacion(@PathVariable Boolean aprobado) {
        return entregableService.obtenerEntregablesPorAprobacion(aprobado);
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public List<Entregable> obtenerEntregablesPorSubidoPor(@PathVariable Long usuarioId) {
        return entregableService.obtenerEntregablesPorSubidoPor(usuarioId);
    }
    
    @PutMapping("/{id}/aprobar/{usuarioId}")
    public ResponseEntity<Entregable> aprobarEntregable(@PathVariable Long id, @PathVariable Long usuarioId) {
        Entregable entregable = entregableService.aprobarEntregable(id, usuarioId);
        if (entregable != null) {
            return ResponseEntity.ok(entregable);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/tarea/{tareaId}/aprobados/{aprobado}")
    public List<Entregable> obtenerEntregablesPorTareaYAprobacion(@PathVariable Long tareaId, @PathVariable Boolean aprobado) {
        return entregableService.obtenerEntregablesPorTareaYAprobacion(tareaId, aprobado);
    }
    
    @GetMapping("/tarea/{tareaId}/versiones")
    public List<Entregable> obtenerUltimasVersionesPorTarea(@PathVariable Long tareaId) {
        return entregableService.obtenerUltimasVersionesPorTarea(tareaId);
    }
}