package paginaAspe.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paginaAspe.Model.CampaniaEstado;
import paginaAspe.Model.Campaña;
import paginaAspe.Repository.CampañaRepository;
import paginaAspe.Service.CampaniaEstadoService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AdminCampaniaController {

    private final CampañaRepository campaniaRepo;
    private final CampaniaEstadoService estadoService;

    public AdminCampaniaController(CampañaRepository campaniaRepo, CampaniaEstadoService estadoService) {
        this.campaniaRepo = campaniaRepo;
        this.estadoService = estadoService;
    }

    // Historial
    @GetMapping("/campanias/{id}/historial")
    public List<CampaniaEstado> historial(@PathVariable Long id) {
        return estadoService.historial(id);
    }

    // Cambio de estado (ADMIN)
    @PatchMapping("/admin/campanias/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String nuevo = body.getOrDefault("estado", "").trim();
        String obs = body.getOrDefault("observacion", "");
        Long uid = null;
        try {
            uid = Long.valueOf(body.getOrDefault("usuarioId", "0"));
        } catch (Exception ignored) {
        }

        if (nuevo.isEmpty())
            return ResponseEntity.badRequest().body(Map.of("error", "Estado requerido"));
        if (!List.of("CREADA", "EN_PROCESO", "COMPLETADA", "RECHAZADA").contains(nuevo)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Estado inválido"));
        }

        Optional<Campaña> op = campaniaRepo.findById(id);
        if (op.isEmpty())
            return ResponseEntity.status(404).body(Map.of("error", "Campaña no encontrada"));

        Campaña c = op.get();
        c.setEstado(nuevo);
        campaniaRepo.save(c);

        CampaniaEstado e = new CampaniaEstado();
        e.setCampaniaId(id);
        e.setEstado(nuevo);
        e.setObservacion(obs);
        e.setUsuarioId(uid);
        estadoService.registrar(e);

        return ResponseEntity.ok(Map.of("ok", true, "estado", nuevo));
    }
}
