package paginaAspe.Model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "auditoria")
public class Auditoria {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditoriaId;
    
    @Column(name = "usuario_id")
    private Long usuarioId; // FK
    
    private String entidad;
    
    @Column(name = "entidad_id")
    private Long entidadId;
    
    private String accion;
    
    @Column(columnDefinition = "TEXT")
    private String cambios;
    
    @Column(name = "creado_at")
    private LocalDateTime creadoAt;
    
    // Constructores
    public Auditoria() {}
    
    public Auditoria(Long usuarioId, String entidad, Long entidadId, String accion, String cambios, LocalDateTime creadoAt) {
        this.usuarioId = usuarioId;
        this.entidad = entidad;
        this.entidadId = entidadId;
        this.accion = accion;
        this.cambios = cambios;
        this.creadoAt = creadoAt;
    }
    
    // Getters y Setters
    public Long getAuditoriaId() {
        return auditoriaId;
    }
    
    public void setAuditoriaId(Long auditoriaId) {
        this.auditoriaId = auditoriaId;
    }
    
    public Long getUsuarioId() {
        return usuarioId;
    }
    
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    public String getEntidad() {
        return entidad;
    }
    
    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }
    
    public Long getEntidadId() {
        return entidadId;
    }
    
    public void setEntidadId(Long entidadId) {
        this.entidadId = entidadId;
    }
    
    public String getAccion() {
        return accion;
    }
    
    public void setAccion(String accion) {
        this.accion = accion;
    }
    
    public String getCambios() {
        return cambios;
    }
    
    public void setCambios(String cambios) {
        this.cambios = cambios;
    }
    
    public LocalDateTime getCreadoAt() {
        return creadoAt;
    }
    
    public void setCreadoAt(LocalDateTime creadoAt) {
        this.creadoAt = creadoAt;
    }
}