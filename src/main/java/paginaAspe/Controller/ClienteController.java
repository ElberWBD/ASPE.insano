package paginaAspe.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paginaAspe.Model.Cliente;
import paginaAspe.Repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    // === LISTAR TODOS LOS CLIENTES ===
    @GetMapping("/listar")
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    // === REGISTRAR NUEVO CLIENTE ===
    @PostMapping("/guardar")
    public ResponseEntity<?> guardar(@RequestBody Cliente cliente) {
        // Si no tiene ID, se considera un nuevo registro
        if (cliente.getId_cliente() == null) {
            // Verifica si el email ya existe
            if (clienteRepository.findByEmail(cliente.getEmail()).isPresent()) {
                return ResponseEntity.status(400).body("El correo ya está registrado");
            }
            Cliente nuevo = clienteRepository.save(cliente);
            return ResponseEntity.ok(nuevo);
        } else {
            // Si tiene ID → actualizar
            Optional<Cliente> clienteExistenteOpt = clienteRepository.findById(cliente.getId_cliente());
            if (clienteExistenteOpt.isPresent()) {
                Cliente existente = clienteExistenteOpt.get();

                existente.setNombre(cliente.getNombre());
                existente.setApellido(cliente.getApellido());
                existente.setRazon_social(cliente.getRazon_social());
                existente.setEmail(cliente.getEmail());
                existente.setTelefono(cliente.getTelefono());

                // ✅ Solo actualiza password si se envía (no borra si viene vacío)
                if (cliente.getPassword() != null && !cliente.getPassword().isEmpty()) {
                    existente.setPassword(cliente.getPassword());
                }

                Cliente actualizado = clienteRepository.save(existente);
                return ResponseEntity.ok(actualizado);
            } else {
                return ResponseEntity.status(404).body("Cliente no encontrado para actualizar");
            }
        }
    }

    // === LOGIN (EMAIL + PASSWORD) ===
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Cliente datosLogin) {
        Optional<Cliente> clienteOpt = clienteRepository.findByEmail(datosLogin.getEmail());

        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            if (cliente.getPassword().equals(datosLogin.getPassword())) {
                return ResponseEntity.ok(cliente);
            } else {
                return ResponseEntity.status(401).body("Contraseña incorrecta");
            }
        }
        return ResponseEntity.status(404).body("Usuario no encontrado");
    }

    // === BUSCAR CLIENTE POR EMAIL ===
    @GetMapping("/buscarPorEmail/{email}")
    public ResponseEntity<?> buscarPorEmail(@PathVariable String email) {
        Optional<Cliente> clienteOpt = clienteRepository.findByEmail(email);
        if (clienteOpt.isPresent()) {
            return ResponseEntity.ok(clienteOpt.get());
        }
        return ResponseEntity.status(404).body("Cliente no encontrado");
    }

    // === BUSCAR CLIENTE POR ID ===
    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(id);
        if (clienteOpt.isPresent()) {
            return ResponseEntity.ok(clienteOpt.get());
        } else {
            return ResponseEntity.status(404).body("Cliente no encontrado");
        }
    }

    // === ACTUALIZAR CLIENTE ===
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Cliente clienteDetalles) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(id);

        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();

            cliente.setNombre(clienteDetalles.getNombre());
            cliente.setApellido(clienteDetalles.getApellido());
            cliente.setRazon_social(clienteDetalles.getRazon_social());
            cliente.setEmail(clienteDetalles.getEmail());
            cliente.setTelefono(clienteDetalles.getTelefono());

            // ✅ Solo actualiza password si viene una nueva
            if (clienteDetalles.getPassword() != null && !clienteDetalles.getPassword().isEmpty()) {
                cliente.setPassword(clienteDetalles.getPassword());
            }

            Cliente actualizado = clienteRepository.save(cliente);
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.status(404).body("Cliente no encontrado");
    }

    // === ELIMINAR CLIENTE ===
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(id);
        if (clienteOpt.isPresent()) {
            clienteRepository.deleteById(id);
            return ResponseEntity.ok("Cliente eliminado correctamente");
        }
        return ResponseEntity.status(404).body("Cliente no encontrado");
    }
}
