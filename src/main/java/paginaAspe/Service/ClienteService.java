package paginaAspe.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import paginaAspe.Model.Cliente;
import paginaAspe.Repository.ClienteRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // 🔹 Registrar cliente
    public Cliente crearCliente(Cliente cliente) {
        cliente.setCreatedAt(LocalDateTime.now());
        return clienteRepository.save(cliente);
    }

    // 🔹 Obtener todos
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteRepository.findAll();
    }

    // 🔹 Obtener por ID
    public Optional<Cliente> obtenerClientePorId(Long id) {
        return clienteRepository.findById(id);
    }

    // 🔹 Obtener por Email
    public Optional<Cliente> obtenerClientePorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    // 🔹 Actualizar
    public Cliente actualizarCliente(Long id, Cliente clienteActualizado) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setRazonSocial(clienteActualizado.getRazonSocial());
                    cliente.setEmail(clienteActualizado.getEmail());
                    cliente.setTelefono(clienteActualizado.getTelefono());
                    cliente.setDireccion(clienteActualizado.getDireccion());
                    cliente.setPassword(clienteActualizado.getPassword());
                    return clienteRepository.save(cliente);
                })
                .orElse(null);
    }

    // 🔹 Eliminar
    public boolean eliminarCliente(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // 🔹 Buscar por razón social
    public List<Cliente> buscarClientesPorRazonSocial(String razonSocial) {
        return clienteRepository.findByRazonSocialContainingIgnoreCase(razonSocial);
    }

    // 🔹 Verificar si existe email
    public boolean existeEmail(String email) {
        return clienteRepository.findByEmail(email).isPresent();
    }

    // 🔹 Obtener recientes
    public List<Cliente> obtenerClientesRecientes() {
        return clienteRepository.findTop5ByOrderByCreatedAtDesc();
    }
}
