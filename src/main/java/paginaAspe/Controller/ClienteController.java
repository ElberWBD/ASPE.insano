package paginaAspe.Controller;

import paginaAspe.Model.Cliente;
import paginaAspe.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // 🔹 Obtener todos los clientes
    @GetMapping
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteService.obtenerTodosLosClientes();
    }

    // 🔹 Obtener cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.obtenerClientePorId(id);
        return cliente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 Obtener cliente por email
    @GetMapping("/email/{email}")
    public ResponseEntity<Cliente> obtenerClientePorEmail(@PathVariable String email) {
        Optional<Cliente> cliente = clienteService.obtenerClientePorEmail(email);
        return cliente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 Registro de cliente
    @PostMapping("/register")
    public ResponseEntity<?> registrarCliente(@RequestBody Cliente cliente) {
        try {
            // Validar que no exista el email
            if (clienteService.existeEmail(cliente.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("El email ya está registrado");
            }

            Cliente nuevoCliente = clienteService.crearCliente(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar el cliente: " + e.getMessage());
        }
    }

    // 🔹 Login de cliente
    @PostMapping("/login")
    public ResponseEntity<?> loginCliente(@RequestBody Cliente datosLogin) {
        Optional<Cliente> clienteOpt = clienteService.obtenerClientePorEmail(datosLogin.getEmail());

        if (clienteOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cliente no encontrado");
        }

        Cliente cliente = clienteOpt.get();

        if (!cliente.getPassword().equals(datosLogin.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Contraseña incorrecta");
        }

        return ResponseEntity.ok(cliente);
    }

    // 🔹 Actualizar cliente
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        Cliente clienteActualizado = clienteService.actualizarCliente(id, cliente);
        if (clienteActualizado != null) {
            return ResponseEntity.ok(clienteActualizado);
        }
        return ResponseEntity.notFound().build();
    }

    // 🔹 Eliminar cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        boolean eliminado = clienteService.eliminarCliente(id);
        if (eliminado) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // 🔹 Buscar clientes por razón social
    @GetMapping("/buscar")
    public List<Cliente> buscarClientesPorRazonSocial(@RequestParam String razonSocial) {
        return clienteService.buscarClientesPorRazonSocial(razonSocial);
    }

    // 🔹 Clientes recientes
    @GetMapping("/recientes")
    public List<Cliente> obtenerClientesRecientes() {
        return clienteService.obtenerClientesRecientes();
    }

    // 🔹 Verificar si un email ya existe
    @GetMapping("/verificar-email/{email}")
    public boolean verificarEmail(@PathVariable String email) {
        return clienteService.existeEmail(email);
    }
}
