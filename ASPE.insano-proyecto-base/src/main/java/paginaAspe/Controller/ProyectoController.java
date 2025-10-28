package paginaAspe.Controller;

import paginaAspe.Model.Proyecto;
import paginaAspe.Service.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/proyectos")
@CrossOrigin(origins = "*")
public class ProyectoController {
    
    @Autowired
    private ProyectoService proyectoService;
    
    @GetMapping
    public List<Proyecto> obtenerTodosLosProyectos() {
        return proyectoService.obtenerTodosLosProyectos();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> obtenerProyectoPorId(@PathVariable Long id) {
        Optional<Proyecto> proyecto = proyectoService.obtenerProyectoPorId(id);
        return proyecto.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Proyecto crearProyecto(@RequestBody Proyecto proyecto) {
        return proyectoService.crearProyecto(proyecto);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> actualizarProyecto(@PathVariable Long id, @RequestBody Proyecto proyecto) {
        Proyecto proyectoActualizado = proyectoService.actualizarProyecto(id, proyecto);
        if (proyectoActualizado != null) {
            return ResponseEntity.ok(proyectoActualizado);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable Long id) {
        boolean eliminado = proyectoService.eliminarProyecto(id);
        if (eliminado) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/cliente/{clienteId}")
    public List<Proyecto> obtenerProyectosPorCliente(@PathVariable Long clienteId) {
        return proyectoService.obtenerProyectosPorCliente(clienteId);
    }
    
    @GetMapping("/estado/{estado}")
    public List<Proyecto> obtenerProyectosPorEstado(@PathVariable String estado) {
        return proyectoService.obtenerProyectosPorEstado(estado);
    }
    
    @GetMapping("/activos")
    public List<Proyecto> obtenerProyectosActivos() {
        return proyectoService.obtenerProyectosActivos();
    }
    
    @GetMapping("/vencidos")
    public List<Proyecto> obtenerProyectosVencidos() {
        return proyectoService.obtenerProyectosVencidos();
    }
    
    @GetMapping("/futuros")
    public List<Proyecto> obtenerProyectosFuturos() {
        return proyectoService.obtenerProyectosFuturos();
    }
    
    @GetMapping("/buscar")
    public List<Proyecto> buscarProyectosPorNombre(@RequestParam String nombre) {
        return proyectoService.buscarProyectosPorNombre(nombre);
    }
    
    @GetMapping("/recientes")
    public List<Proyecto> obtenerProyectosRecientes() {
        return proyectoService.obtenerProyectosRecientes();
    }
    
    @GetMapping("/cliente/{clienteId}/estado/{estado}")
    public List<Proyecto> obtenerProyectosPorClienteYEstado(
            @PathVariable Long clienteId, 
            @PathVariable String estado) {
        return proyectoService.obtenerProyectosPorClienteYEstado(clienteId, estado);
    }
    
    @GetMapping("/presupuesto")
    public List<Proyecto> obtenerProyectosPorRangoPresupuesto(
            @RequestParam BigDecimal min, 
            @RequestParam BigDecimal max) {
        return proyectoService.obtenerProyectosPorRangoPresupuesto(min, max);
    }
    
    @GetMapping("/estadisticas/estado/{estado}")
    public Long contarProyectosPorEstado(@PathVariable String estado) {
        return proyectoService.contarProyectosPorEstado(estado);
    }
    
    @GetMapping("/estadisticas/cliente/{clienteId}")
    public Long contarProyectosPorCliente(@PathVariable Long clienteId) {
        return proyectoService.contarProyectosPorCliente(clienteId);
    }
    
    @GetMapping("/verificar-nombre/{nombre}")
    public boolean verificarNombreProyecto(@PathVariable String nombre) {
        return proyectoService.existeNombreProyecto(nombre);
    }
}