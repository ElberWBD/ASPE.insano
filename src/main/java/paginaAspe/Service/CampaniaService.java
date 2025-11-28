package paginaAspe.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paginaAspe.Model.Campania;
import paginaAspe.Repository.CampaniaRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CampaniaService {

    private final CampaniaRepository repo;

    public CampaniaService(CampaniaRepository repo) {
        this.repo = repo;
    }

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

    public List<Campania> obtenerPorCliente(Long clienteId) {
        return repo.findByIdCliente(clienteId);
    }

    public List<Campania> obtenerPorUsuario(Long usuarioId) {
        return repo.findByUsuarioId(usuarioId);
    }

    public Campania asignarUsuario(Long campaniaId, Long usuarioId) {
        Optional<Campania> opt = repo.findById(campaniaId);
        if (opt.isPresent()) {
            Campania c = opt.get();
            c.setUsuarioId(usuarioId);
            // al asignar un usuario podemos cambiar estado a 'Asignada'
            if (c.getEstado() == null || c.getEstado().isEmpty()) {
                c.setEstado("Asignada");
            }
            return repo.save(c);
        }
        return null;
    }

    public Campania actualizarEstado(Long campaniaId, String estado) {
        Optional<Campania> opt = repo.findById(campaniaId);
        if (opt.isPresent()) {
            Campania c = opt.get();
            c.setEstado(estado);
            return repo.save(c);
        }
        return null;
    }

    


}
