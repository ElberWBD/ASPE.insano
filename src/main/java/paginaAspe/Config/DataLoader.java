package paginaAspe.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import paginaAspe.Model.Rol;
import paginaAspe.Model.Usuario;
import paginaAspe.Repository.UsuarioRepository;
import paginaAspe.Repository.RolRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Configuration
public class DataLoader {
 
    @Bean
    CommandLineRunner seed(RolRepository rolRepo, UsuarioRepository userRepo) {
        return args -> {
            Rol admin = rolRepo.findByNombre("ADMIN").orElseGet(() -> {
                Rol r = new Rol();
                r.setNombre("ADMIN");
                r.setDescripcion("Administrador del sistema");
                return rolRepo.save(r);
            });
            Rol cliente = rolRepo.findByNombre("CLIENTE").orElseGet(() -> {
                Rol r = new Rol();
                r.setNombre("CLIENTE");
                r.setDescripcion("Cliente");
                return rolRepo.save(r);
            });

            userRepo.findByEmail("admin@aspe.pe").orElseGet(() -> {
                Usuario u = new Usuario();
                u.setUsername("admin");
                u.setEmail("admin@aspe.pe");
                u.setPasswordHash(BCrypt.hashpw("Admin123*", BCrypt.gensalt(12)));
                u.setRol(admin);
                u.setActive(true);
                return userRepo.save(u);
            });
        };
    }
}
