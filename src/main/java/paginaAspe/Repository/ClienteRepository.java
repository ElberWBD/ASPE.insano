package paginaAspe.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paginaAspe.Model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
