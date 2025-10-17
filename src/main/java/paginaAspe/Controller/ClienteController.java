package paginaAspe.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import paginaAspe.Model.Cliente;
import paginaAspe.Service.ClienteService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/listar")
    public List<Cliente> listar() {
        return clienteService.listar();
    }

    @PostMapping("/guardar")
    public Cliente guardar(@RequestBody Cliente cliente) {
        return clienteService.guardar(cliente);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
    }
}
