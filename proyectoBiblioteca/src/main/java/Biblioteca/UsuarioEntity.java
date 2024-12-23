package Biblioteca;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "usuario")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "dni", nullable = false, length = 15)
    private String dni;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "penalizacionHasta")
    private Date penalizacionHasta;

    @OneToMany(mappedBy = "usuarioByUsuarioId")
    private Collection<PrestamoEntity> prestamosById;

    public UsuarioEntity(String dniUsuario, String nombre, String email, String passwordUsuario, String tipo) {
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getPenalizacionHasta() {
        return penalizacionHasta;
    }

    public void setPenalizacionHasta(Date penalizacionHasta) {
        this.penalizacionHasta = penalizacionHasta;
    }

    public Collection<PrestamoEntity> getPrestamosById() {
        return prestamosById;
    }

    public void setPrestamosById(Collection<PrestamoEntity> prestamosById) {
        this.prestamosById = prestamosById;
    }
}
