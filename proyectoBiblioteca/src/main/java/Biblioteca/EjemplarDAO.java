package Biblioteca;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.Date;

public class EjemplarDAO {

    private EntityManager em;
    private EntityTransaction tx;

    public EjemplarDAO(EntityManager em) {
        this.em = em;
        this.tx = em.getTransaction();
    }

    // Buscar un ejemplar por su ID
    public EjemplarEntity buscarPorId(int id) {
        return em.find(EjemplarEntity.class, id);  // Utilizamos find para buscar por id
    }

    // Buscar un ejemplar por su ISBN
    public EjemplarEntity buscarPorIsbn(String isbn) {
        return em.createQuery("SELECT e FROM EjemplarEntity e WHERE e.isbn = :isbn", EjemplarEntity.class)
                .setParameter("isbn", isbn)
                .getResultStream()
                .findFirst()
                .orElse(null);  // Devuelve null si no encuentra el ejemplar
    }

    // Guardar un nuevo ejemplar
    public void guardar(EjemplarEntity ejemplar) {
        tx.begin();
        em.persist(ejemplar);
        tx.commit();
    }

    // Actualizar un ejemplar
    public void actualizar(EjemplarEntity ejemplar) {
        tx.begin();
        em.merge(ejemplar);
        tx.commit();
    }

    // Eliminar un ejemplar
    public void eliminar(EjemplarEntity ejemplar) {
        tx.begin();
        em.remove(ejemplar);
        tx.commit();
    }

    // Contar ejemplares disponibles para un libro
    public long contarEjemplaresDisponibles(String isbn) {
        return em.createQuery("SELECT COUNT(e) FROM EjemplarEntity e WHERE e.isbn = :isbn AND e.estado = 'Disponible'", Long.class)
                .setParameter("isbn", isbn)
                .getSingleResult();
    }

    // Devolver un préstamo y aplicar penalización si es necesario
    public void devolverPrestamo(int prestamoId) {
        PrestamoEntity prestamo = em.find(PrestamoEntity.class, prestamoId);
        Date fechaDevolucion = new Date(System.currentTimeMillis());
        prestamo.setFechaDevolucion((java.sql.Date) fechaDevolucion);

        // Comprobar si la devolución es fuera de plazo
        long diferenciaEnMilisegundos = fechaDevolucion.getTime() - prestamo.getFechaInicio().getTime();
        long diasDeRetraso = diferenciaEnMilisegundos / (1000 * 60 * 60 * 24);

        if (diasDeRetraso > 15) {
            UsuarioDAO usuarioDAO = new UsuarioDAO(em);
            usuarioDAO.registrarPenalizacion(prestamo.getUsuarioId(), 1);  // Suponiendo que solo se penaliza por un libro fuera de plazo
        }

        // Actualizamos el estado del ejemplar a "Disponible"
        EjemplarDAO ejemplarDAO = new EjemplarDAO(em);
        EjemplarEntity ejemplar = ejemplarDAO.buscarPorId(prestamo.getEjemplarId());
        ejemplar.setEstado("Disponible");
        ejemplarDAO.actualizar(ejemplar);

        tx.begin();
        em.merge(prestamo);
        tx.commit();
    }


}
