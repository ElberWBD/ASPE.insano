package paginaAspe.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import paginaAspe.Model.Campania;
import paginaAspe.Service.CampaniaService;
import paginaAspe.dto.SolicitudCampaniaDTO;

@RestController
@RequestMapping("/api/campanias")
@CrossOrigin(origins = "*") // puedes limitar al origen de tu frontend si quieres
public class CampaniaController {

    private final CampaniaService service;

    public CampaniaController(CampaniaService service) {
        this.service = service;
    }

    // ===================== CRUD BÁSICO =====================

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
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
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
            existing.setUsuarioId(datos.getUsuarioId());

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

    // ===================== CONSULTAS =====================

    @GetMapping("/cliente/{clienteId}")
    public List<Campania> listarPorCliente(@PathVariable Long clienteId) {
        return service.obtenerPorCliente(clienteId);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Campania> listarPorUsuario(@PathVariable Long usuarioId) {
        return service.obtenerPorUsuario(usuarioId);
    }

    // ===================== ASIGNACIONES =====================

    /**
     * Asignar un solo usuario (compatibilidad con lo que ya usas).
     */
    @PutMapping("/{id}/asignar/{usuarioId}")
    public ResponseEntity<Campania> asignarUsuario(@PathVariable Long id, @PathVariable Long usuarioId) {
        Campania result = service.asignarUsuario(id, usuarioId);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Asignar múltiples usuarios a la campaña.
     * Devuelve la lista de campañas resultantes (campaña original + copias).
     */
    @PostMapping("/{id}/asignar-multiples")
    public ResponseEntity<List<Campania>> asignarMultiples(
            @PathVariable Long id,
            @RequestBody List<Long> usuariosIds) {

        List<Campania> result = service.asignarMultiplesUsuarios(id, usuariosIds);
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    // ===================== ESTADOS =====================

    /**
     * Endpoint genérico que ya tenías para cambiar estado.
     */
    @PutMapping("/{id}/estado")
    public ResponseEntity<Campania> actualizarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String nuevoEstado = body.get("estado");
        Campania result = service.actualizarEstado(id, nuevoEstado);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Cliente → solicitar cierre de campaña.
     * Estado pasa a "PENDIENTE_CIERRE".
     */
    @PostMapping("/{id}/solicitar-cierre")
    public ResponseEntity<Campania> solicitarCierre(@PathVariable Long id) {
        Campania result = service.marcarPendienteCierre(id);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Admin → aceptar cierre de campaña.
     * Estado pasa a "FINALIZADA".
     */
    @PostMapping("/{id}/aceptar-cierre")
    public ResponseEntity<Campania> aceptarCierre(@PathVariable Long id) {
        Campania result = service.cerrarCampania(id);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }

    // ===================== SOLICITUD DESDE PORTAL CLIENTE =====================

    /**
     * Portal del cliente → "Solicitar campaña" desde el carrito.
     * Recibe un SolicitudCampaniaDTO y crea una campaña en estado "PENDIENTE_ASIGNACION".
     */
    @PostMapping("/solicitar")
    public ResponseEntity<?> solicitarCampania(@RequestBody SolicitudCampaniaDTO dto) {
        try {
            Campania camp = service.crearDesdeSolicitud(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(camp);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
