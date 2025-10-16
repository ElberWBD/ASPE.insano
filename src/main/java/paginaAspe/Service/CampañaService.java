package paginaAspe.Service;

import paginaAspe.Model.Campaña;
import paginaAspe.Repository.CampañaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CampañaService {

    @Autowired
    private CampañaRepository campañaRepository;

    // Obtener todas las campañas
    public List<Campaña> obtenerTodasLasCampañas() {
        return campañaRepository.findAll();
    }

    // Obtener campaña por ID
    public Optional<Campaña> obtenerCampañaPorId(Long id) {
        return campañaRepository.findById(id);
    }

    // Guardar campaña
    public Campaña guardarCampaña(Campaña campaña) {
        return campañaRepository.save(campaña);
    }

    // Crear nueva campaña
    public Campaña crearCampaña(Campaña campaña) {
        return campañaRepository.save(campaña);
    }

    // Actualizar campaña
    public Campaña actualizarCampaña(Long id, Campaña campañaDetalles) {
        Optional<Campaña> optionalCampaña = campañaRepository.findById(id);

        if (optionalCampaña.isPresent()) {
            Campaña campaña = optionalCampaña.get();
            campaña.setProyectoId(campañaDetalles.getProyectoId());
            campaña.setClienteId(campañaDetalles.getClienteId());
            campaña.setNombre(campañaDetalles.getNombre());
            campaña.setDescripcion(campañaDetalles.getDescripcion());
            campaña.setFechaInicio(campañaDetalles.getFechaInicio());
            campaña.setFechaFin(campañaDetalles.getFechaFin());
            campaña.setPresupuesto(campañaDetalles.getPresupuesto());
            campaña.setEstado(campañaDetalles.getEstado());

            return campañaRepository.save(campaña);
        }
        return null;
    }

    public boolean cotizar(Long campañaId, BigDecimal precio, Integer dias) {
        Optional<Campaña> opt = campañaRepository.findById(campañaId);
        if (opt.isEmpty())
            return false;

        Campaña c = opt.get();
        c.setPrecioPropuesto(precio); // requiere campo + setter en Campaña.java (Sección 2)
        c.setDiasEstimados(dias); // requiere campo + setter en Campaña.java (Sección 2)
        c.setEstado("COTIZADA"); // manteniendo tu estado como String

        campañaRepository.save(c);
        return true;
    }

    public boolean decidirCliente(Long campañaId, boolean acepta) {
        Optional<Campaña> opt = campañaRepository.findById(campañaId);
        if (opt.isEmpty())
            return false;

        Campaña c = opt.get();
        c.setEstado(acepta ? "ACEPTADA" : "RECHAZADA");
        campañaRepository.save(c);
        return true;
    }

    // Eliminar campaña
    public boolean eliminarCampaña(Long id) {
        if (campañaRepository.existsById(id)) {
            campañaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Obtener campañas por proyecto
    public List<Campaña> obtenerCampañasPorProyecto(Long proyectoId) {
        return campañaRepository.findByProyectoId(proyectoId);
    }

    // Obtener campañas por cliente
    public List<Campaña> obtenerCampañasPorCliente(Long clienteId) {
        return campañaRepository.findByClienteId(clienteId);
    }

    // Obtener campañas por estado
    public List<Campaña> obtenerCampañasPorEstado(String estado) {
        return campañaRepository.findByEstado(estado);
    }

    // Obtener campañas activas
    public List<Campaña> obtenerCampañasActivas() {
        LocalDate hoy = LocalDate.now();
        return campañaRepository.findByFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(hoy, hoy);
    }

    // Obtener campañas vencidas
    public List<Campaña> obtenerCampañasVencidas() {
        return campañaRepository.findByFechaFinBefore(LocalDate.now());
    }

    // Obtener campañas futuras
    public List<Campaña> obtenerCampañasFuturas() {
        return campañaRepository.findByFechaInicioAfter(LocalDate.now());
    }

    // Obtener campañas por proyecto y estado
    public List<Campaña> obtenerCampañasPorProyectoYEstado(Long proyectoId, String estado) {
        return campañaRepository.findByProyectoIdAndEstado(proyectoId, estado);
    }

    // Obtener campañas recientes
    public List<Campaña> obtenerCampañasRecientes() {
        return campañaRepository.findByOrderByFechaInicioDesc();
    }

    // Buscar campañas por nombre
    public List<Campaña> buscarCampañasPorNombre(String nombre) {
        return campañaRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // Obtener campañas por rango de presupuesto
    public List<Campaña> obtenerCampañasPorRangoPresupuesto(BigDecimal min, BigDecimal max) {
        List<Campaña> todas = campañaRepository.findAll();
        return todas.stream()
                .filter(c -> c.getPresupuesto() != null)
                .filter(c -> c.getPresupuesto().compareTo(min) >= 0 && c.getPresupuesto().compareTo(max) <= 0)
                .toList();
    }

    // Contar campañas por estado
    public Long contarCampañasPorEstado(String estado) {
        return campañaRepository.countByEstado(estado);
    }

    // Contar campañas por cliente
    public Long contarCampañasPorCliente(Long clienteId) {
        return campañaRepository.countByClienteId(clienteId);
    }

    // Verificar si existe nombre de campaña
    public boolean existeNombreCampaña(String nombre) {
        return campañaRepository.existsByNombre(nombre);
    }
}