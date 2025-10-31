package paginaAspe.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paginaAspe.Model.Campania;
import java.util.List;

@Repository
public interface CampaniaRepository extends JpaRepository<Campania, Long> {
    // Obtiene todas las campañas creadas por un cliente específico
    List<Campania> findByIdCliente(Long idCliente);

    // Obtiene todas las campañas asignadas a un usuario específico
    List<Campania> findByUsuarioId(Long usuarioId);
}
