package paginaAspe.Model;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "proyectos")
public class Proyecto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long proyectoId;
    
    @Column(name = "cliente_id")
    private Long clienteId; // FK
    
    private String nombre;
    
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;
    
    @Column(name = "fecha_fin")
    private LocalDate fechaFin;
    
    private BigDecimal presupuesto;
    
    private String estado;
    
    // Constructores
    public Proyecto() {}
    
    public Proyecto(Long clienteId, String nombre, String descripcion, 
                   LocalDate fechaInicio, LocalDate fechaFin, BigDecimal presupuesto, String estado) {
        this.clienteId = clienteId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.presupuesto = presupuesto;
        this.estado = estado;
    }
    
    // Getters y Setters
    public Long getProyectoId() {
        return proyectoId;
    }
    
    public void setProyectoId(Long proyectoId) {
        this.proyectoId = proyectoId;
    }
    
    public Long getClienteId() {
        return clienteId;
    }
    
    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    
    public LocalDate getFechaFin() {
        return fechaFin;
    }
    
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    public BigDecimal getPresupuesto() {
        return presupuesto;
    }
    
    public void setPresupuesto(BigDecimal presupuesto) {
        this.presupuesto = presupuesto;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
}