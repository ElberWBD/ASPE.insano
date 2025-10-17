package paginaAspe.Model;

import jakarta.persistence.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "reporte")
public class Reporte {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reporteId;
    
    @Column(name = "campaña_id")
    private Long campañaId; // FK
    
    @Column(name = "generado_por")
    private Long generadoPor; // FK (Usuario)
    
    @Column(name = "generado_at")
    private LocalDateTime generadoAt;
    
    private String tipo;
    
    @Column(name = "storage_key")
    private String storageKey;
    
    // Constructores
    public Reporte() {}
    
    public Reporte(Long campañaId, Long generadoPor, LocalDateTime generadoAt, String tipo, String storageKey) {
        this.campañaId = campañaId;
        this.generadoPor = generadoPor;
        this.generadoAt = generadoAt;
        this.tipo = tipo;
        this.storageKey = storageKey;
    }
    
    // Getters y Setters
    public Long getReporteId() {
        return reporteId;
    }
    
    public void setReporteId(Long reporteId) {
        this.reporteId = reporteId;
    }
    
    public Long getCampañaId() {
        return campañaId;
    }
    
    public void setCampañaId(Long campañaId) {
        this.campañaId = campañaId;
    }
    
    public Long getGeneradoPor() {
        return generadoPor;
    }
    
    public void setGeneradoPor(Long generadoPor) {
        this.generadoPor = generadoPor;
    }
    
    public LocalDateTime getGeneradoAt() {
        return generadoAt;
    }
    
    public void setGeneradoAt(LocalDateTime generadoAt) {
        this.generadoAt = generadoAt;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getStorageKey() {
        return storageKey;
    }
    
    public void setStorageKey(String storageKey) {
        this.storageKey = storageKey;
    }
}