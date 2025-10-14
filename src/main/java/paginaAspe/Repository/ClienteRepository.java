package paginaAspe.Repository;

import paginaAspe.Model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    // Buscar cliente por email
    Optional<Cliente> findByEmail(String email);
    
    // Buscar clientes por razón social (búsqueda parcial)
    List<Cliente> findByRazonSocialContainingIgnoreCase(String razonSocial);
    
    // Verificar si existe un cliente con el email
    Boolean existsByEmail(String email);
    
    // Buscar clientes por teléfono
    List<Cliente> findByTelefono(String telefono);
    
    // Buscar clientes ordenados por fecha de creación
    List<Cliente> findByOrderByCreatedAtDesc();
    
    // Buscar clientes por razón social exacta
    Optional<Cliente> findByRazonSocial(String razonSocial);
     List<Cliente> findTop5ByOrderByCreatedAtDesc();
}