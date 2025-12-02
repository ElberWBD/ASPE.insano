package paginaAspe.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import paginaAspe.Model.Campania;
import paginaAspe.Repository.CampaniaRepository;
import paginaAspe.dto.SolicitudCampaniaDTO;

@Service
@Transactional
public class CampaniaService {

    private final CampaniaRepository repo;

    public CampaniaService(CampaniaRepository repo) {
        this.repo = repo;
    }

    // ===================== CRUD BÁSICO =====================

    public List<Campania> listar() {
        return repo.findAll();
    }

    public Optional<Campania> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public Campania guardar(Campania c) {
        return repo.save(c);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    // ===================== CONSULTAS =====================

    public List<Campania> obtenerPorCliente(Long clienteId) {
        return repo.findByIdCliente(clienteId);
    }

    public List<Campania> obtenerPorUsuario(Long usuarioId) {
        return repo.findByUsuarioId(usuarioId);
    }

    // ===================== ASIGNACIÓN DE USUARIO =====================

    /**
     * Asigna un único usuario (trabajador) a la campaña.
     * Ya existía: lo mantenemos para compatibilidad.
     */
    public Campania asignarUsuario(Long campaniaId, Long usuarioId) {
        Optional<Campania> opt = repo.findById(campaniaId);
        if (opt.isPresent()) {
            Campania c = opt.get();
            c.setUsuarioId(usuarioId);
            // al asignar un usuario podemos cambiar estado a 'Asignada' si está vacío
            if (c.getEstado() == null || c.getEstado().isEmpty()) {
                c.setEstado("Asignada");
            }
            return repo.save(c);
        }
        return null;
    }

    /**
     * Asigna la campaña a uno o varios usuarios.
     *
     * Estrategia con el modelo ACTUAL (solo un usuarioId en Campania):
     * - Para el primer usuario, se usa la campaña original.
     * - Para los demás usuarios, se CLONA la campaña (nuevo registro con otro usuarioId).
     *
     * De esta forma, cada trabajador ve su "propia" campaña en /api/campanias/usuario/{id}.
     */
    public List<Campania> asignarMultiplesUsuarios(Long campaniaId, List<Long> usuariosIds) {
        Optional<Campania> opt = repo.findById(campaniaId);
        if (opt.isEmpty() || usuariosIds == null || usuariosIds.isEmpty()) {
            return List.of();
        }

        Campania original = opt.get();
        List<Campania> result = new ArrayList<>();

        boolean primera = true;
        for (Long usuarioId : usuariosIds) {
            if (usuarioId == null) continue;

            if (primera) {
                // usamos la campaña original para el primer usuario
                original.setUsuarioId(usuarioId);
                if (original.getEstado() == null || original.getEstado().isEmpty()) {
                    original.setEstado("Asignada");
                }
                result.add(repo.save(original));
                primera = false;
            } else {
                // para el resto, clonamos la campaña
                Campania copia = new Campania();
                copia.setIdCliente(original.getIdCliente());
                copia.setUsuarioId(usuarioId);
                copia.setNombre(original.getNombre());
                copia.setDescripcion(original.getDescripcion());
                copia.setFechaInicio(original.getFechaInicio());
                copia.setFechaFin(original.getFechaFin());
                copia.setPresupuesto(original.getPresupuesto());
                copia.setEstado(
                        (original.getEstado() == null || original.getEstado().isEmpty())
                                ? "Asignada"
                                : original.getEstado()
                );
                result.add(repo.save(copia));
            }
        }
        return result;
    }

    // ===================== ESTADO =====================

    public Campania actualizarEstado(Long campaniaId, String estado) {
        Optional<Campania> opt = repo.findById(campaniaId);
        if (opt.isPresent()) {
            Campania c = opt.get();
            c.setEstado(estado);
            return repo.save(c);
        }
        return null;
    }

    /**
     * Marca la campaña como PENDIENTE_CIERRE (solicitud desde el cliente).
     */
    public Campania marcarPendienteCierre(Long campaniaId) {
        Optional<Campania> opt = repo.findById(campaniaId);
        if (opt.isPresent()) {
            Campania c = opt.get();
            c.setEstado("PENDIENTE_CIERRE");
            return repo.save(c);
        }
        return null;
    }

    /**
     * Marca la campaña como FINALIZADA (aceptación desde el admin).
     */
    public Campania cerrarCampania(Long campaniaId) {
        Optional<Campania> opt = repo.findById(campaniaId);
        if (opt.isPresent()) {
            Campania c = opt.get();
            c.setEstado("FINALIZADA");
            return repo.save(c);
        }
        return null;
    }

    // ===================== SOLICITUD DESDE PORTAL CLIENTE =====================

    /**
     * Crea una campaña a partir de la solicitud del portal del cliente.
     * Estado inicial: PENDIENTE_ASIGNACION
     */
    public Campania crearDesdeSolicitud(SolicitudCampaniaDTO dto) {
        if (dto == null || dto.getClienteId() == null) {
            throw new IllegalArgumentException("clienteId es obligatorio");
        }

        Campania c = new Campania();
        c.setIdCliente(dto.getClienteId());
        c.setNombre(
                dto.getNombre() != null && !dto.getNombre().isBlank()
                        ? dto.getNombre()
                        : "Campaña solicitada por cliente " + dto.getClienteId()
        );
        c.setDescripcion(dto.getDescripcion());
        c.setPresupuesto(
    dto.getPresupuesto() != null 
        ? java.math.BigDecimal.valueOf(dto.getPresupuesto()) 
        : null
);

        c.setFechaInicio(LocalDate.now());
        c.setEstado("PENDIENTE_ASIGNACION");

        // usuarioId se asignará después por el admin (asignarUsuario / asignarMultiplesUsuarios)

        return repo.save(c);
    }
}
