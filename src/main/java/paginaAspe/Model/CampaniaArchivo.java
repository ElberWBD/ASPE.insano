package paginaAspe.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "campania_archivos")
public class CampaniaArchivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // NOTA: mantenemos ID simple para no romper tu flujo actual en el
    // controller/service
    @Column(name = "campania_id", nullable = false)
    private Long campaniaId;

    // "ADMIN" o "CLIENTE" según tu controller actual
    @Column(nullable = false)
    private String remitente;

    @Column(name = "nombre_original")
    private String nombreOriginal;

    @Column(name = "mime_type")
    private String mimeType;

    private long tamanio;

    // Ruta absoluta o relativa en disco
    private String ruta;

    private LocalDateTime fechaSubida = LocalDateTime.now();

    // ===== Getters / Setters =====
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCampaniaId() {
        return campaniaId;
    }

    public void setCampaniaId(Long campaniaId) {
        this.campaniaId = campaniaId;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getNombreOriginal() {
        return nombreOriginal;
    }

    public void setNombreOriginal(String nombreOriginal) {
        this.nombreOriginal = nombreOriginal;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public long getTamanio() {
        return tamanio;
    }

    public void setTamanio(long tamanio) {
        this.tamanio = tamanio;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public LocalDateTime getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(LocalDateTime fechaSubida) {
        this.fechaSubida = fechaSubida;
    }
}
