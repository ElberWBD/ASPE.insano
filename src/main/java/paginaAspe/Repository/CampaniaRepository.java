package paginaAspe.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paginaAspe.Model.Campania;

@Repository
public interface CampaniaRepository extends JpaRepository<Campania, Long> {
}
