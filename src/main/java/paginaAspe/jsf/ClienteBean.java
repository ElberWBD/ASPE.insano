package paginaAspe.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import jakarta.annotation.PostConstruct;
import paginaAspe.Model.Cliente;
import paginaAspe.Repository.ClienteRepository;

/**
 * Managed Bean JSF para gestionar la vista de clientes.
 * Se consume desde JSF con el nombre 'clienteBean'.
 */
@Component("clienteBean")
@SessionScope   // Bean de sesión manejado por Spring
public class ClienteBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ClienteRepository clienteRepository;

    private List<Cliente> clientes = new ArrayList<>();
    private Cliente clienteSeleccionado = new Cliente();
    private Cliente nuevoCliente = new Cliente();

    private String filtro;
    private int porcentajeActivos;

    @PostConstruct
    public void init() {
        cargarClientes();
        calcularPorcentajeActivos();

        if (!clientes.isEmpty()) {
            clienteSeleccionado = clientes.get(0);
        }
    }

    public void cargarClientes() {
        clientes = clienteRepository.findAll();
    }

    public void seleccionarCliente(Cliente c) {
        this.clienteSeleccionado = (c != null) ? c : new Cliente();
    }

    public void guardarNuevo() {
        if (nuevoCliente == null) return;
        if (nuevoCliente.getEmail() == null || nuevoCliente.getEmail().isBlank()) return;

        if (clienteRepository.findByEmail(nuevoCliente.getEmail()).isPresent()) {
            return; // email duplicado
        }

        clienteRepository.save(nuevoCliente);
        nuevoCliente = new Cliente();

        cargarClientes();
        calcularPorcentajeActivos();
    }

    public void actualizarSeleccionado() {
        if (clienteSeleccionado == null || clienteSeleccionado.getId_cliente() == null) return;

        Optional<Cliente> opt = clienteRepository.findById(clienteSeleccionado.getId_cliente());
        if (opt.isEmpty()) return;

        Cliente existente = opt.get();
        existente.setNombre(clienteSeleccionado.getNombre());
        existente.setApellido(clienteSeleccionado.getApellido());
        existente.setRazon_social(clienteSeleccionado.getRazon_social());
        existente.setEmail(clienteSeleccionado.getEmail());
        existente.setTelefono(clienteSeleccionado.getTelefono());

        if (clienteSeleccionado.getPassword() != null &&
            !clienteSeleccionado.getPassword().isEmpty()) {
            existente.setPassword(clienteSeleccionado.getPassword());
        }

        clienteRepository.save(existente);
        cargarClientes();
        calcularPorcentajeActivos();
    }

    public void eliminar(Cliente c) {
        if (c == null || c.getId_cliente() == null) return;

        clienteRepository.deleteById(c.getId_cliente());
        cargarClientes();
        calcularPorcentajeActivos();
    }

    public void limpiarFiltro() {
        this.filtro = "";
    }

    private void calcularPorcentajeActivos() {
        if (clientes == null || clientes.isEmpty()) {
            porcentajeActivos = 0;
            return;
        }

        long activos = clientes.stream()
                .filter(c -> c.getEmail() != null && !c.getEmail().isBlank())
                .count();

        porcentajeActivos = (int) Math.round((activos * 100.0) / clientes.size());
    }

    // ===================== GETTERS / SETTERS =====================

    public List<Cliente> getClientes() {
        // Para mantener coherencia con el resto del sistema,
        // recargamos antes de devolver la lista
        cargarClientes();
        calcularPorcentajeActivos();

        if (filtro == null || filtro.isBlank()) {
            return clientes;
        }

        String t = filtro.toLowerCase();

        return clientes.stream()
                .filter(c ->
                        String.valueOf(c.getId_cliente()).toLowerCase().contains(t) ||
                        safe(c.getNombre()).toLowerCase().contains(t) ||
                        safe(c.getApellido()).toLowerCase().contains(t) ||
                        safe(c.getEmail()).toLowerCase().contains(t) ||
                        safe(c.getRazon_social()).toLowerCase().contains(t))
                .collect(Collectors.toList());
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Cliente getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public void setClienteSeleccionado(Cliente clienteSeleccionado) {
        this.clienteSeleccionado = clienteSeleccionado;
    }

    public Cliente getNuevoCliente() {
        return nuevoCliente;
    }

    public void setNuevoCliente(Cliente nuevoCliente) {
        this.nuevoCliente = nuevoCliente;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public int getPorcentajeActivos() {
        return porcentajeActivos;
    }

    public String getNombreCompletoSeleccionado() {
        if (clienteSeleccionado == null) return "";
        return (safe(clienteSeleccionado.getNombre()) + " " +
                safe(clienteSeleccionado.getApellido())).trim();
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }
}
