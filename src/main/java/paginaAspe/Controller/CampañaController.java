package paginaAspe.Controller;

import paginaAspe.Model.Campaña;
import paginaAspe.Service.CampañaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/campanias")
@CrossOrigin(origins = "*")
public class CampañaController {
    
    @Autowired
    private CampañaService campañaService;
    
    @GetMapping
    public List<Campaña> obtenerTodasLasCampañas() {
        return campañaService.obtenerTodasLasCampañas();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Campaña> obtenerCampañaPorId(@PathVariable Long id) {
        Optional<Campaña> campaña = campañaService.obtenerCampañaPorId(id);
        return campaña.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Campaña crearCampaña(@RequestBody Campaña campaña) {
        return campañaService.crearCampaña(campaña);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Campaña> actualizarCampaña(@PathVariable Long id, @RequestBody Campaña campaña) {
        Campaña campañaActualizada = campañaService.actualizarCampaña(id, campaña);
        if (campañaActualizada != null) {
            return ResponseEntity.ok(campañaActualizada);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCampaña(@PathVariable Long id) {
        boolean eliminado = campañaService.eliminarCampaña(id);
        if (eliminado) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/proyecto/{proyectoId}")
    public List<Campaña> obtenerCampañasPorProyecto(@PathVariable Long proyectoId) {
        return campañaService.obtenerCampañasPorProyecto(proyectoId);
    }
    
    @GetMapping("/cliente/{clienteId}")
    public List<Campaña> obtenerCampañasPorCliente(@PathVariable Long clienteId) {
        return campañaService.obtenerCampañasPorCliente(clienteId);
    }
    
    @GetMapping("/estado/{estado}")
    public List<Campaña> obtenerCampañasPorEstado(@PathVariable String estado) {
        return campañaService.obtenerCampañasPorEstado(estado);
    }
    
    @GetMapping("/activas")
    public List<Campaña> obtenerCampañasActivas() {
        return campañaService.obtenerCampañasActivas();
    }
    
    @GetMapping("/vencidas")
    public List<Campaña> obtenerCampañasVencidas() {
        return campañaService.obtenerCampañasVencidas();
    }
    
    @GetMapping("/futuras")
    public List<Campaña> obtenerCampañasFuturas() {
        return campañaService.obtenerCampañasFuturas();
    }
    
    @GetMapping("/buscar")
    public List<Campaña> buscarCampañasPorNombre(@RequestParam String nombre) {
        return campañaService.buscarCampañasPorNombre(nombre);
    }
    
    @GetMapping("/recientes")
    public List<Campaña> obtenerCampañasRecientes() {
        return campañaService.obtenerCampañasRecientes();
    }
    
    @GetMapping("/proyecto/{proyectoId}/estado/{estado}")
    public List<Campaña> obtenerCampañasPorProyectoYEstado(
            @PathVariable Long proyectoId, 
            @PathVariable String estado) {
        return campañaService.obtenerCampañasPorProyectoYEstado(proyectoId, estado);
    }
    
    @GetMapping("/presupuesto")
    public List<Campaña> obtenerCampañasPorRangoPresupuesto(
            @RequestParam BigDecimal min, 
            @RequestParam BigDecimal max) {
        return campañaService.obtenerCampañasPorRangoPresupuesto(min, max);
    }
    
    @GetMapping("/estadisticas/estado/{estado}")
    public Long contarCampañasPorEstado(@PathVariable String estado) {
        return campañaService.contarCampañasPorEstado(estado);
    }
    
    @GetMapping("/estadisticas/cliente/{clienteId}")
    public Long contarCampañasPorCliente(@PathVariable Long clienteId) {
        return campañaService.contarCampañasPorCliente(clienteId);
    }
    
    @GetMapping("/verificar-nombre/{nombre}")
    public boolean verificarNombreCampaña(@PathVariable String nombre) {
        return campañaService.existeNombreCampaña(nombre);
    }
}