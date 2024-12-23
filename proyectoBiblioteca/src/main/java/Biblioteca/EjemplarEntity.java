package Biblioteca;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "ejemplar")
public class EjemplarEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "isbn", nullable = false, length = 20)
    private String isbn;
    @Basic
    @Column(name = "estado", nullable = true)
    private Object estado;
    @ManyToOne
    @JoinColumn(name = "isbn", referencedColumnName = "isbn", nullable = false)
    private LibroEntity libroByIsbn;
    @OneToMany(mappedBy = "ejemplarByEjemplarId")
    private Collection<PrestamoEntity> prestamosById;

    public EjemplarEntity(LibroEntity libroEjemplar, String estado) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getEstado() {
        return (String) estado;
    }

    public void setEstado(Object estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EjemplarEntity that = (EjemplarEntity) o;

        if (id != that.id) return false;
        if (isbn != null ? !isbn.equals(that.isbn) : that.isbn != null) return false;
        if (estado != null ? !estado.equals(that.estado) : that.estado != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        return result;
    }

    public LibroEntity getLibroByIsbn() {
        return libroByIsbn;
    }

    public void setLibroByIsbn(LibroEntity libroByIsbn) {
        this.libroByIsbn = libroByIsbn;
    }

    public Collection<PrestamoEntity> getPrestamosById() {
        return prestamosById;
    }

    public void setPrestamosById(Collection<PrestamoEntity> prestamosById) {
        this.prestamosById = prestamosById;
    }
}
