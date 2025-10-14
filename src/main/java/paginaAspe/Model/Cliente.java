package paginaAspe.Model;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    private Long clienteId;

    @Column(name = "razon_social", nullable = false, length = 100)
    private String razonSocial;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "direccion", length = 200)
    private String direccion;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    // Constructores
    public Cliente() {}

    public Cliente(String razonSocial, String email, String telefono, String direccion, String password, LocalDateTime createdAt) {
        this.razonSocial = razonSocial;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.password = password;
        this.createdAt = createdAt;
    }

    // Getters y Setters
    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
