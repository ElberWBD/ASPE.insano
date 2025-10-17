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
}
