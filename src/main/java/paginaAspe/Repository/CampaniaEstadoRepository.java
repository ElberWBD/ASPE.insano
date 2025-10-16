package paginaAspe.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paginaAspe.Model.CampaniaEstado;

import java.util.List;

@Repository
public interface CampaniaEstadoRepository extends JpaRepository<CampaniaEstado, Long> {
    List<CampaniaEstado> findByCampaniaIdOrderByEventoEnDesc(Long campaniaId);
}
