package paginaAspe.Controller;

import paginaAspe.Model.Reporte;
import paginaAspe.Service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*")
public class ReporteController {
    
    @Autowired
    private ReporteService reporteService;
    
    @GetMapping
    public List<Reporte> obtenerTodosLosReportes() {
        return reporteService.obtenerReportesRecientes();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Reporte> obtenerReportePorId(@PathVariable Long id) {
        Optional<Reporte> reporte = reporteService.obtenerReportePorId(id);
        return reporte.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Reporte crearReporte(@RequestBody Reporte reporte) {
        return reporteService.guardarReporte(reporte);
    }
    
    // Endpoint rápido para crear reporte
    @PostMapping("/crear")
    public Reporte crearReporte(
            @RequestParam Long campañaId,
            @RequestParam Long generadoPor,
            @RequestParam String tipo,
            @RequestParam String storageKey) {
        return reporteService.crearReporte(campañaId, generadoPor, tipo, storageKey);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReporte(@PathVariable Long id) {
        boolean eliminado = reporteService.eliminarReporte(id);
        if (eliminado) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/campaña/{campañaId}")
    public List<Reporte> obtenerReportesPorCampaña(@PathVariable Long campañaId) {
        return reporteService.obtenerReportesPorCampaña(campañaId);
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public List<Reporte> obtenerReportesPorUsuario(@PathVariable Long usuarioId) {
        return reporteService.obtenerReportesPorUsuario(usuarioId);
    }
    
    @GetMapping("/tipo/{tipo}")
    public List<Reporte> obtenerReportesPorTipo(@PathVariable String tipo) {
        return reporteService.obtenerReportesPorTipo(tipo);
    }
    
    @GetMapping("/campaña/{campañaId}/tipo/{tipo}")
    public List<Reporte> obtenerReportesPorCampañaYTipo(
            @PathVariable Long campañaId, 
            @PathVariable String tipo) {
        return reporteService.obtenerReportesPorCampañaYTipo(campañaId, tipo);
    }
    
    @GetMapping("/fechas")
    public List<Reporte> obtenerReportesPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return reporteService.obtenerReportesPorRangoFechas(inicio, fin);
    }
    
    @GetMapping("/storage/{storageKey}")
    public ResponseEntity<Reporte> obtenerReportePorStorageKey(@PathVariable String storageKey) {
        Optional<Reporte> reporte = reporteService.obtenerReportePorStorageKey(storageKey);
        return reporte.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/estadisticas/tipo/{tipo}")
    public Long contarReportesPorTipo(@PathVariable String tipo) {
        return reporteService.contarReportesPorTipo(tipo);
    }
    
    @GetMapping("/estadisticas/campaña/{campañaId}")
    public Long contarReportesPorCampaña(@PathVariable Long campañaId) {
        return reporteService.contarReportesPorCampaña(campañaId);
    }
    
    @GetMapping("/verificar-storage/{storageKey}")
    public boolean verificarStorageKey(@PathVariable String storageKey) {
        return reporteService.existeStorageKey(storageKey);
    }
}