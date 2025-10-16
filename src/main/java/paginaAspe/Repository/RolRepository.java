package paginaAspe.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import paginaAspe.Model.Rol;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);
}
 