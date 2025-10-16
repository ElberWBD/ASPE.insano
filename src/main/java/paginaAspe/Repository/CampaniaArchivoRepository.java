package paginaAspe.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import paginaAspe.Model.CampaniaArchivo;

import java.util.List;

public interface CampaniaArchivoRepository extends JpaRepository<CampaniaArchivo, Long> {

    // Coincide con el nombre del campo en la entidad: private Long campaniaId;
    List<CampaniaArchivo> findByCampaniaId(Long campaniaId);
}
