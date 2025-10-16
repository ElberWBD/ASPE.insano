package paginaAspe.Controller;

import org.springframework.web.bind.annotation.*;
import paginaAspe.Repository.CampañaRepository;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final CampañaRepository repo;

    public DashboardController(CampañaRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/admin/resumen")
    public Map<String, Long> resumenAdmin() {
        long creada = repo.countByEstado("CREADA");
        long cotizada = repo.countByEstado("COTIZADA");
        long aceptada = repo.countByEstado("ACEPTADA");
        long rechazada = repo.countByEstado("RECHAZADA");
        long proceso = repo.countByEstado("EN_PROCESO");
        long completada = repo.countByEstado("COMPLETADA");
        return Map.of(
                "CREADA", creada,
                "COTIZADA", cotizada,
                "ACEPTADA", aceptada,
                "RECHAZADA", rechazada,
                "EN_PROCESO", proceso,
                "COMPLETADA", completada);
    }
}
