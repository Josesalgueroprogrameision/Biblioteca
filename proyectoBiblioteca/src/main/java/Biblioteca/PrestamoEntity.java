package Biblioteca;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "prestamo")
public class PrestamoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "usuario_id", nullable = false)
    private int usuarioId;
    @Basic
    @Column(name = "ejemplar_id", nullable = false)
    private int ejemplarId;
    @Basic
    @Column(name = "fechaInicio", nullable = false)
    private Date fechaInicio;
    @Basic
    @Column(name = "fechaDevolucion", nullable = true)
    private Date fechaDevolucion;
    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false)
    private UsuarioEntity usuarioByUsuarioId;
    @ManyToOne
    @JoinColumn(name = "ejemplar_id", referencedColumnName = "id", nullable = false)
    private EjemplarEntity ejemplarByEjemplarId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getEjemplarId() {
        return ejemplarId;
    }

    public void setEjemplarId(int ejemplarId) {
        this.ejemplarId = ejemplarId;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrestamoEntity that = (PrestamoEntity) o;

        if (id != that.id) return false;
        if (usuarioId != that.usuarioId) return false;
        if (ejemplarId != that.ejemplarId) return false;
        if (fechaInicio != null ? !fechaInicio.equals(that.fechaInicio) : that.fechaInicio != null) return false;
        if (fechaDevolucion != null ? !fechaDevolucion.equals(that.fechaDevolucion) : that.fechaDevolucion != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + usuarioId;
        result = 31 * result + ejemplarId;
        result = 31 * result + (fechaInicio != null ? fechaInicio.hashCode() : 0);
        result = 31 * result + (fechaDevolucion != null ? fechaDevolucion.hashCode() : 0);
        return result;
    }

    public UsuarioEntity getUsuarioByUsuarioId() {
        return usuarioByUsuarioId;
    }

    public void setUsuarioByUsuarioId(UsuarioEntity usuarioByUsuarioId) {
        this.usuarioByUsuarioId = usuarioByUsuarioId;
    }

    public EjemplarEntity getEjemplarByEjemplarId() {
        return ejemplarByEjemplarId;
    }

    public void setEjemplarByEjemplarId(EjemplarEntity ejemplarByEjemplarId) {
        this.ejemplarByEjemplarId = ejemplarByEjemplarId;
    }
}
