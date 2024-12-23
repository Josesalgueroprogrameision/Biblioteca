package Biblioteca;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class LibroDAO {

    private EntityManager em;
    private EntityTransaction tx;

    public LibroDAO(EntityManager em) {
        this.em = em;
        this.tx = em.getTransaction();
    }

    // Buscar un libro por su ISBN
    public LibroEntity buscarPorIsbn(String isbn) {
        return em.find(LibroEntity.class, isbn);  // Utilizamos find para buscar por isbn
    }

    // Buscar un libro por su título
    public LibroEntity buscarPorTitulo(String titulo) {
        return em.createQuery("SELECT l FROM LibroEntity l WHERE l.titulo = :titulo", LibroEntity.class)
                .setParameter("titulo", titulo)
                .getResultStream()
                .findFirst()
                .orElse(null);  // Devuelve null si no encuentra el libro
    }

    // Guardar un nuevo libro
    public void guardar(LibroEntity libro) {
        tx.begin();
        em.persist(libro);
        tx.commit();
    }

    // Actualizar un libro
    public void actualizar(LibroEntity libro) {
        tx.begin();
        em.merge(libro);
        tx.commit();
    }

    // Eliminar un libro
    public void eliminar(LibroEntity libro) {
        tx.begin();
        em.remove(libro);
        tx.commit();
    }
}
