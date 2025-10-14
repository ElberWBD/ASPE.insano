package paginaAspe.Model;
import jakarta.persistence.*;

@Entity
@Table(name = "rol")
public class Rol {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rolId;
    
    @Column(nullable = false, unique = true)
    private String nombre;
    
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    
    // Constructores
    public Rol() {}
    
    public Rol(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    // Getters y Setters
    public Long getRolId() {
        return rolId;
    }
    
    public void setRolId(Long rolId) {
        this.rolId = rolId;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}