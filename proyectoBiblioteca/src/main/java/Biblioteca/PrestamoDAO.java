package Biblioteca;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.Date;
import java.util.List;

public class PrestamoDAO {

    private EntityManager em;
    private EntityTransaction tx;

    public PrestamoDAO(EntityManager em) {
        this.em = em;
        this.tx = em.getTransaction();
    }

    // Buscar un préstamo por su ID
    public PrestamoEntity buscarPorId(int id) {
        return em.find(PrestamoEntity.class, id);  // Utilizamos find para buscar por ID
    }

    // Guardar un nuevo préstamo
    public void guardar(PrestamoEntity prestamo) {
        tx.begin();
        em.persist(prestamo);
        tx.commit();
    }

    // Actualizar un préstamo
    public void actualizar(PrestamoEntity prestamo) {
        tx.begin();
        em.merge(prestamo);
        tx.commit();
    }

    // Eliminar un préstamo
    public void eliminar(PrestamoEntity prestamo) {
        tx.begin();
        em.remove(prestamo);
        tx.commit();
    }

    // Verificar si el usuario tiene menos de 3 préstamos activos
    public boolean tieneMenosDeTresPrestamosActivos(int usuarioId) {
        long prestamosActivos = em.createQuery("SELECT COUNT(p) FROM PrestamoEntity p WHERE p.usuarioId = :usuarioId AND p.fechaDevolucion IS NULL", Long.class)
                .setParameter("usuarioId", usuarioId)
                .getSingleResult();
        return prestamosActivos < 3;
    }

    // Verificar si el usuario tiene una penalización activa
    public boolean tienePenalizacionActiva(int usuarioId) {
        Date penalizacionHasta = em.createQuery("SELECT u.penalizacionHasta FROM UsuarioEntity u WHERE u.id = :usuarioId", Date.class)
                .setParameter("usuarioId", usuarioId)
                .getSingleResult();
        return penalizacionHasta != null && penalizacionHasta.after(new Date(System.currentTimeMillis()));
    }

    // Verificar si el ejemplar está disponible
    public boolean ejemplarDisponible(int ejemplarId) {
        EjemplarEntity ejemplar = em.find(EjemplarEntity.class, ejemplarId);
        return ejemplar != null && "Disponible".equalsIgnoreCase(ejemplar.getEstado());
    }

    // Registrar un préstamo
    public void registrarPrestamo(PrestamoEntity prestamo) {
        if (!tieneMenosDeTresPrestamosActivos(prestamo.getUsuarioId())) {
            throw new IllegalStateException("El usuario no puede tener más de 3 préstamos activos.");
        }
        if (!ejemplarDisponible(prestamo.getEjemplarId())) {
            throw new IllegalStateException("El ejemplar no está disponible.");
        }

        if (tienePenalizacionActiva(prestamo.getUsuarioId())) {
            throw new IllegalStateException("El usuario tiene una penalización activa.");
        }

        tx.begin();
        em.persist(prestamo);
        tx.commit();
    }

    // Consultar todos los préstamos
    public PrestamoEntity[] consultarTodos() {
        List<PrestamoEntity> prestamos = em.createQuery("SELECT p FROM PrestamoEntity p", PrestamoEntity.class).getResultList();
        return prestamos.toArray(new PrestamoEntity[0]);
    }

    // Consultar los préstamos por ID de usuario
    public PrestamoEntity[] consultarPorUsuarioId(int id) {
        List<PrestamoEntity> prestamos = em.createQuery("SELECT p FROM PrestamoEntity p WHERE p.usuarioId = :usuarioId", PrestamoEntity.class)
                .setParameter("usuarioId", id)
                .getResultList();
        return prestamos.toArray(new PrestamoEntity[0]);
    }

    // Registrar la devolución de un préstamo
    public void registrarDevolucion(PrestamoEntity prestamo) {
        tx.begin();
        prestamo.setFechaDevolucion((java.sql.Date) new Date(System.currentTimeMillis()));  // Establecer la fecha de devolución actual
        em.merge(prestamo);
        tx.commit();
    }
}
