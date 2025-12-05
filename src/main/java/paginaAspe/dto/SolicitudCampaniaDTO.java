package paginaAspe.dto;

import java.util.List;

public class SolicitudCampaniaDTO {

    private Long clienteId;
    private String nombre;        // opcional, se puede generar por defecto
    private String descripcion;   // resumen que escribe el cliente
    private Double presupuesto;   // opcional
    private List<Long> serviciosIds; // ids de servicios seleccionados en "Tu Pedido"

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

    public Double getPresupuesto() {
        return presupuesto;
    }
    public void setPresupuesto(Double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public List<Long> getServiciosIds() {
        return serviciosIds;
    }
    public void setServiciosIds(List<Long> serviciosIds) {
        this.serviciosIds = serviciosIds;
    }
}
