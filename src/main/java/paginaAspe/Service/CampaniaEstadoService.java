package paginaAspe.Service;

import org.springframework.stereotype.Service;
import paginaAspe.Model.CampaniaEstado;
import paginaAspe.Repository.CampaniaEstadoRepository;

import java.util.List;

@Service
public class CampaniaEstadoService {

    private final CampaniaEstadoRepository repo;

    public CampaniaEstadoService(CampaniaEstadoRepository repo) {
        this.repo = repo;
    }

    public List<CampaniaEstado> historial(Long campaniaId) {
        return repo.findByCampaniaIdOrderByEventoEnDesc(campaniaId);
    }

    public CampaniaEstado registrar(CampaniaEstado e) {
        return repo.save(e);
    }
}
