package paginaAspe.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "entregable")
public class Entregable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entregableId;
    
    @Column(name = "tarea_id")
    private Long tareaId; // FK
    
    private String titulo;
    
    private Integer version;
    
    @Column(name = "storage_key")
    private String storageKey;
    
    @Column(name = "mime_type")
    private String mimeType;
    
    @Column(name = "subido_por")
    private Long subidoPor; // FK (Usuario)
    
    @Column(name = "subido_at")
    private LocalDateTime subidoAt;
    
    private Boolean aprobado;
    
    @Column(name = "aprobado_por")
    private Long aprobadoPor; // FK (Usuario)
    
    @Column(name = "aprobado_at")
    private LocalDateTime aprobadoAt;
    
    // Constructores
    public Entregable() {}
    
    public Entregable(Long tareaId, String titulo, Integer version, String storageKey, 
                     String mimeType, Long subidoPor, LocalDateTime subidoAt, 
                     Boolean aprobado, Long aprobadoPor, LocalDateTime aprobadoAt) {
        this.tareaId = tareaId;
        this.titulo = titulo;
        this.version = version;
        this.storageKey = storageKey;
        this.mimeType = mimeType;
        this.subidoPor = subidoPor;
        this.subidoAt = subidoAt;
        this.aprobado = aprobado;
        this.aprobadoPor = aprobadoPor;
        this.aprobadoAt = aprobadoAt;
    }
    
    // Getters y Setters
    public Long getEntregableId() {
        return entregableId;
    }
    
    public void setEntregableId(Long entregableId) {
        this.entregableId = entregableId;
    }
    
    public Long getTareaId() {
        return tareaId;
    }
    
    public void setTareaId(Long tareaId) {
        this.tareaId = tareaId;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public Integer getVersion() {
        return version;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
    }
    
    public String getStorageKey() {
        return storageKey;
    }
    
    public void setStorageKey(String storageKey) {
        this.storageKey = storageKey;
    }
    
    public String getMimeType() {
        return mimeType;
    }
    
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
    
    public Long getSubidoPor() {
        return subidoPor;
    }
    
    public void setSubidoPor(Long subidoPor) {
        this.subidoPor = subidoPor;
    }
    
    public LocalDateTime getSubidoAt() {
        return subidoAt;
    }
    
    public void setSubidoAt(LocalDateTime subidoAt) {
        this.subidoAt = subidoAt;
    }
    
    public Boolean getAprobado() {
        return aprobado;
    }
    
    public void setAprobado(Boolean aprobado) {
        this.aprobado = aprobado;
    }
    
    public Long getAprobadoPor() {
        return aprobadoPor;
    }
    
    public void setAprobadoPor(Long aprobadoPor) {
        this.aprobadoPor = aprobadoPor;
    }
    
    public LocalDateTime getAprobadoAt() {
        return aprobadoAt;
    }
    
    public void setAprobadoAt(LocalDateTime aprobadoAt) {
        this.aprobadoAt = aprobadoAt;
    }
}