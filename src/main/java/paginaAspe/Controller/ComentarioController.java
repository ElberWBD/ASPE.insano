package paginaAspe.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paginaAspe.Model.Comentario;
import paginaAspe.Repository.ComentarioRepository;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/campanias/{campaniaId}/comentarios")
@CrossOrigin("*")
public class ComentarioController {

    private final ComentarioRepository comentarioRepository;

    @Autowired
    public ComentarioController(ComentarioRepository comentarioRepository) {
        this.comentarioRepository = comentarioRepository;
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
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }
}
