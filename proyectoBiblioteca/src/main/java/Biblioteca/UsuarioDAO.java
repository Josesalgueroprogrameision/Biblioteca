package Biblioteca;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.Date;

public class UsuarioDAO {

    private EntityManager em;
    private EntityTransaction tx;

    public UsuarioDAO(EntityManager em) {
        this.em = em;
        this.tx = em.getTransaction();
    }

    public UsuarioDAO() {

    }

    // Buscar un usuario por su DNI
    public UsuarioEntity buscarPorDni(String dni) {
        return em.createQuery("SELECT u FROM UsuarioEntity u WHERE u.dni = :dni", UsuarioEntity.class)
                .setParameter("dni", dni)
                .getResultStream()
                .findFirst()
                .orElse(null); // Devuelve null si no encuentra el usuario
    }

    // Buscar un usuario por su ID
    public UsuarioEntity buscarPorId(int id) {
        return em.find(UsuarioEntity.class, id); // Utilizamos find para buscar por ID
    }

    // Guardar un nuevo usuario
    public void guardar(UsuarioEntity usuario) {
        tx.begin();
        em.persist(usuario);
        tx.commit();
    }

    // Actualizar un usuario
    public void actualizar(UsuarioEntity usuario) {
        tx.begin();
        em.merge(usuario);
        tx.commit();
    }

    // Eliminar un usuario
    public void eliminar(UsuarioEntity usuario) {
        tx.begin();
        em.remove(usuario);
        tx.commit();
    }

    // Registrar penalización a un usuario
    public void registrarPenalizacion(int usuarioId, int cantidadLibrosFueraPlazo) {
        Date penalizacionHasta = new Date(System.currentTimeMillis() + cantidadLibrosFueraPlazo * 15L * 24 * 60 * 60 * 1000);

        // Verificar si el usuario existe antes de aplicar la penalización
        UsuarioEntity usuario = em.find(UsuarioEntity.class, usuarioId);
        if (usuario != null) {
            usuario.setPenalizacionHasta(new java.sql.Date(penalizacionHasta.getTime()));
            tx.begin();
            em.merge(usuario);
            tx.commit();
        }
    }

    // Obtener usuario por DNI y contraseña
    public UsuarioEntity obtenerPorDniYContrasena(String dni, String password) {
        return this.em.createQuery("SELECT u FROM UsuarioEntity u WHERE u.dni = :dni AND u.password = :password", UsuarioEntity.class)
                .setParameter("dni", dni)
                .setParameter("password", password)
                .getResultStream()
                .findFirst()
                .orElse(null); // Devuelve null si no encuentra coincidencia
    }
}
