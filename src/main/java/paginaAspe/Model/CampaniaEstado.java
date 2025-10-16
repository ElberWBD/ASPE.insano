package paginaAspe.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "campania_estados")
public class CampaniaEstado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "campania_id", nullable = false)
    private Long campaniaId;

    @Column(name = "estado", nullable = false, length = 30)
    private String estado; // CREADA, EN_PROCESO, COMPLETADA, RECHAZADA

    @Column(name = "observacion")
    private String observacion;

    @Column(name = "usuario_id")
    private Long usuarioId; // quién cambió (admin)

    @Column(name = "evento_en", nullable = false)
    private LocalDateTime eventoEn = LocalDateTime.now();

    // getters/setters
    public Long getId() {
        return id;
    }

    public Long getCampaniaId() {
        return campaniaId;
    }

    public void setCampaniaId(Long campaniaId) {
        this.campaniaId = campaniaId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LocalDateTime getEventoEn() {
        return eventoEn;
    }

    public void setEventoEn(LocalDateTime eventoEn) {
        this.eventoEn = eventoEn;
    }
}
