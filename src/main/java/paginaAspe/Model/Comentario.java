package paginaAspe.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comentarios")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComentario;

    @Column(name = "campania_id", nullable = false)
    private Long campaniaId;

    @Column(name = "autor_id", nullable = false)
    private Long autorId;

    @Column(name = "mensaje", columnDefinition = "TEXT")
    private String mensaje;

    @Column(name = "archivo_base64")
    private String archivoBase64;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    public Comentario() {
    }

    public Comentario(Long campaniaId, Long autorId, String mensaje, String archivoBase64, LocalDateTime fecha) {
        this.campaniaId = campaniaId;
        this.autorId = autorId;
        this.mensaje = mensaje;
        this.archivoBase64 = archivoBase64;
        this.fecha = fecha;
    }

    public Long getIdComentario() {
        return idComentario;
    }

    public Long getCampaniaId() {
        return campaniaId;
    }

    public void setCampaniaId(Long campaniaId) {
        this.campaniaId = campaniaId;
    }

    public Long getAutorId() {
        return autorId;
    }

    public void setAutorId(Long autorId) {
        this.autorId = autorId;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getArchivoBase64() {
        return archivoBase64;
    }

    public void setArchivoBase64(String archivoBase64) {
        this.archivoBase64 = archivoBase64;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
