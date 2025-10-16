package paginaAspe.Model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "campanias")
public class Campaña {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long campañaId;

    @Column(name = "proyecto_id")
    private Long proyectoId; // FK

    @Column(name = "cliente_id")
    private Long clienteId; // FK

    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    // Presupuesto referencial ingresado por el cliente
    private BigDecimal presupuesto;

    // Estado actual de la campaña (CREADA, COTIZADA, ACEPTADA, RECHAZADA,
    // EN_PROCESO, COMPLETADA)
    private String estado;

    // ====== NUEVOS CAMPOS (para la cotización del ADMIN) ======

    // Monto propuesto por el administrador al cotizar
    @Column(name = "precio_propuesto")
    private BigDecimal precioPropuesto;

    // Días estimados propuestos por el administrador
    @Column(name = "dias_estimados")
    private Integer diasEstimados;

    // ====== Constructores ======
    public Campaña() {
    }

    public Campaña(Long proyectoId, Long clienteId, String nombre, String descripcion,
            LocalDate fechaInicio, LocalDate fechaFin, BigDecimal presupuesto, String estado) {
        this.proyectoId = proyectoId;
        this.clienteId = clienteId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.presupuesto = presupuesto;
        this.estado = estado;
    }

    // Valor por defecto de estado si no se envía
    @PrePersist
    public void prePersist() {
        if (this.estado == null || this.estado.isBlank()) {
            this.estado = "CREADA";
        }
    }

    // ====== Getters y Setters ======
    public Long getCampañaId() {
        return campañaId;
    }

    public void setCampañaId(Long campañaId) {
        this.campañaId = campañaId;
    }

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

    public BigDecimal getPrecioPropuesto() {
        return precioPropuesto;
    }

    public void setPrecioPropuesto(BigDecimal precioPropuesto) {
        this.precioPropuesto = precioPropuesto;
    }

    public Integer getDiasEstimados() {
        return diasEstimados;
    }

    public void setDiasEstimados(Integer diasEstimados) {
        this.diasEstimados = diasEstimados;
    }
}
