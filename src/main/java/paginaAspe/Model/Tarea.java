package paginaAspe.Model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "tarea")
public  class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tareaId;
    
    @Column(name = "campaña_id")
    private Long campañaId; // FK
    
    @Column(nullable = false)
    private String titulo;
    
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;
    
    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;
    
    private String estado;
    
    private Integer prioridad;
    
    // Constructores
    public Tarea() {}
    
    public Tarea(Long campañaId, String titulo, String descripcion, LocalDate fechaInicio, 
                 LocalDate fechaVencimiento, String estado, Integer prioridad) {
        this.campañaId = campañaId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaVencimiento = fechaVencimiento;
        this.estado = estado;
        this.prioridad = prioridad;
    }
    
    // Getters y Setters
    public Long getTareaId() {
        return tareaId;
    }
    
    public void setTareaId(Long tareaId) {
        this.tareaId = tareaId;
    }
    
    public Long getCampañaId() {
        return campañaId;
    }
    
    public void setCampañaId(Long campañaId) {
        this.campañaId = campañaId;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }
    
    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public Integer getPrioridad() {
        return prioridad;
    }
    
    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }
}