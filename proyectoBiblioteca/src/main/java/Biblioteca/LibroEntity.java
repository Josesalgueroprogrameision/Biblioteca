package Biblioteca;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "libro")
public class LibroEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "isbn", nullable = false, length = 20)
    private String isbn;
    @Basic
    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;
    @Basic
    @Column(name = "autor", nullable = false, length = 100)
    private String autor;
    @OneToMany(mappedBy = "libroByIsbn")
    private Collection<EjemplarEntity> ejemplarsByIsbn;

    public LibroEntity(String isbn, String titulo, String autor) {
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LibroEntity that = (LibroEntity) o;

        if (isbn != null ? !isbn.equals(that.isbn) : that.isbn != null) return false;
        if (titulo != null ? !titulo.equals(that.titulo) : that.titulo != null) return false;
        if (autor != null ? !autor.equals(that.autor) : that.autor != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = isbn != null ? isbn.hashCode() : 0;
        result = 31 * result + (titulo != null ? titulo.hashCode() : 0);
        result = 31 * result + (autor != null ? autor.hashCode() : 0);
        return result;
    }

    public Collection<EjemplarEntity> getEjemplarsByIsbn() {
        return ejemplarsByIsbn;
    }

    public void setEjemplarsByIsbn(Collection<EjemplarEntity> ejemplarsByIsbn) {
        this.ejemplarsByIsbn = ejemplarsByIsbn;
    }
}
