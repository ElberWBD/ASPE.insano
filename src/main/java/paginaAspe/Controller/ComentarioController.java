package paginaAspe.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paginaAspe.Model.Comentario;
import paginaAspe.Repository.ComentarioRepository;
import paginaAspe.handler.ComentarioWebSocketHandler;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/campanias/{campaniaId}/comentarios")
@CrossOrigin("*")
public class ComentarioController {

    private final ComentarioRepository comentarioRepository;
    private final ComentarioWebSocketHandler comentarioWebSocketHandler;

    @Autowired
    public ComentarioController(ComentarioRepository comentarioRepository,
                                ComentarioWebSocketHandler comentarioWebSocketHandler) {
        this.comentarioRepository = comentarioRepository;
        this.comentarioWebSocketHandler = comentarioWebSocketHandler;
    }

    @GetMapping
    public ResponseEntity<List<Comentario>> listarComentarios(@PathVariable Long campaniaId) {
        List<Comentario> comentarios = comentarioRepository.findByCampaniaIdOrderByFechaAsc(campaniaId);
        return ResponseEntity.ok(comentarios);
    }

    @PostMapping
    public ResponseEntity<Comentario> crearComentario(@PathVariable Long campaniaId,
                                                      @RequestBody Comentario comentario) {
        comentario.setCampaniaId(campaniaId);
        comentario.setFecha(LocalDateTime.now());
        Comentario creado = comentarioRepository.save(comentario);

        // Notificar a los clientes conectados
        try {
            comentarioWebSocketHandler.enviarComentario(campaniaId, creado);
        } catch (Exception e) {
            e.printStackTrace(); 
        }

        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }
}
