package paginaAspe.Controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import paginaAspe.Model.CampaniaArchivo;
import paginaAspe.Service.CampaniaArchivoService;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/archivos")
@CrossOrigin(origins = "*")
public class ArchivoController {

    private final CampaniaArchivoService service;

    public ArchivoController(CampaniaArchivoService service) {
        this.service = service;
    }

    @PostMapping(value = "/subir", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> subir(@RequestPart("meta") Map<String, Object> meta,
            @RequestPart("file") MultipartFile file) throws IOException {
        CampaniaArchivo a = new CampaniaArchivo();
        a.setCampaniaId(((Number) meta.get("campaniaId")).longValue());
        a.setRemitente((String) meta.get("remitente")); // "ADMIN" o "CLIENTE"
        a = service.guardar(a, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(a);
    }

    @GetMapping("/{archivoId}/descargar")
    public ResponseEntity<byte[]> descargar(@PathVariable Long archivoId) throws IOException {
        // Para simplicidad: carga por ID -> ruta
        var opt = service.listarPorCampania(-1L).stream().filter(a -> a.getId().equals(archivoId)).findFirst();
        if (opt.isEmpty())
            return ResponseEntity.notFound().build();
        CampaniaArchivo a = opt.get();
        byte[] bytes = service.leerBytes(a.getRuta());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + a.getNombreOriginal() + "\"")
                .contentType(MediaType
                        .parseMediaType(a.getMimeType() != null ? a.getMimeType() : "application/octet-stream"))
                .body(bytes);
    }
}
