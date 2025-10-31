package paginaAspe.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paginaAspe.Model.Campania;
import paginaAspe.Service.CampaniaService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/campanias")
@CrossOrigin(origins = "*") // puedes limitar al origen de tu frontend si quieres
public class CampaniaController {

    private final CampaniaService service;

    public CampaniaController(CampaniaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Campania> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Campania> obtener(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Campania campania) {
        // validación simple: idCliente no null
        if (campania.getIdCliente() == null) {
            return ResponseEntity.badRequest().body("El campo idCliente es obligatorio.");
        }
        Campania guardada = service.guardar(campania);
        return ResponseEntity.status(201).body(guardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Campania> actualizar(@PathVariable Long id, @RequestBody Campania datos) {
        return service.buscarPorId(id).map(existing -> {
            existing.setIdCliente(datos.getIdCliente());
            existing.setNombre(datos.getNombre());
            existing.setDescripcion(datos.getDescripcion());
            existing.setFechaInicio(datos.getFechaInicio());
            existing.setFechaFin(datos.getFechaFin());
            existing.setPresupuesto(datos.getPresupuesto());
            existing.setEstado(datos.getEstado());

            Campania saved = service.guardar(existing);
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (service.buscarPorId(id).isPresent()) {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Campania> listarPorCliente(@PathVariable Long clienteId) {
        return service.obtenerPorCliente(clienteId);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Campania> listarPorUsuario(@PathVariable Long usuarioId) {
        return service.obtenerPorUsuario(usuarioId);
    }

    @PutMapping("/{id}/asignar/{usuarioId}")
    public ResponseEntity<Campania> asignarUsuario(@PathVariable Long id, @PathVariable Long usuarioId) {
        Campania result = service.asignarUsuario(id, usuarioId);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Campania> actualizarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String nuevoEstado = body.get("estado");
        Campania result = service.actualizarEstado(id, nuevoEstado);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }

}
