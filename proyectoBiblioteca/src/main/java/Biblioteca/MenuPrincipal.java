package Biblioteca;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Scanner;

public class MenuPrincipal {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unidad-equipo");
        EntityManager em = emf.createEntityManager();

        UsuarioDAO usuarioDAO = new UsuarioDAO(em);
        PrestamoDAO prestamoDAO = new PrestamoDAO(em);
        LibroDAO libroDAO = new LibroDAO(em);
        EjemplarDAO ejemplarDAO = new EjemplarDAO(em);

        Scanner scanner = new Scanner(System.in);

        // Menú principal
        System.out.println("Bienvenido al sistema de la Biblioteca");
        System.out.println("Por favor, ingrese su DNI:");
        String dni = scanner.nextLine();
        System.out.println("Por favor, ingrese su contraseña:");
        String password = scanner.nextLine();

        // Verificar si las credenciales son correctas
        UsuarioEntity usuario = usuarioDAO.obtenerPorDniYContrasena(dni, password);

        if (usuario == null) {
            System.out.println("Credenciales incorrectas. Acceso denegado.");
            return;
        }

        String tipoUsuario = usuario.getTipo();

        System.out.println("Bienvenido, " + usuario.getNombre() + " (" + tipoUsuario + ")");
        boolean salir = false;

        while (!salir) {
            if ("administrador".equalsIgnoreCase(tipoUsuario)) {
                // Menú administrador
                System.out.println("\n--- MENÚ ADMINISTRADOR ---");
                System.out.println("1. Registrar nuevo libro");
                System.out.println("2. Registrar ejemplar");
                System.out.println("3. Registrar usuario");
                System.out.println("4. Consultar préstamos");
                System.out.println("5. Registrar penalización");
                System.out.println("6. Salir");
                System.out.print("Elija una opción: ");
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer

                switch (opcion) {
                    case 1:
                        System.out.println("Registrar nuevo libro");
                        System.out.println("Ingrese ISBN13:");
                        String isbn = scanner.nextLine();
                        System.out.println("Ingrese título:");
                        String titulo = scanner.nextLine();
                        System.out.println("Ingrese autor:");
                        String autor = scanner.nextLine();
                        LibroEntity libro = new LibroEntity(isbn, titulo, autor);
                        libroDAO.guardar(libro);
                        System.out.println("Libro registrado con éxito.");
                        break;
                    case 2:
                        System.out.println("Registrar ejemplar");
                        System.out.println("Ingrese ISBN del libro asociado:");
                        isbn = scanner.nextLine();
                        LibroEntity libroEjemplar = libroDAO.buscarPorIsbn(isbn);
                        if (libroEjemplar == null) {
                            System.out.println("El libro no existe. Registre el libro primero.");
                            break;
                        }
                        System.out.println("Ingrese estado del ejemplar (Disponible/Prestado/Dañado):");
                        String estado = scanner.nextLine();
                        EjemplarEntity ejemplar = new EjemplarEntity(libroEjemplar, estado);
                        ejemplarDAO.guardar(ejemplar);
                        System.out.println("Ejemplar registrado con éxito.");
                        break;
                    case 3:
                        System.out.println("Registrar usuario");
                        System.out.println("Ingrese DNI:");
                        String dniUsuario = scanner.nextLine();
                        System.out.println("Ingrese nombre:");
                        String nombre = scanner.nextLine();
                        System.out.println("Ingrese email:");
                        String email = scanner.nextLine();
                        System.out.println("Ingrese contraseña:");
                        String passwordUsuario = scanner.nextLine();
                        System.out.println("Ingrese tipo (normal/administrador):");
                        String tipo = scanner.nextLine();
                        UsuarioEntity nuevoUsuario = new UsuarioEntity(dniUsuario, nombre, email, passwordUsuario, tipo);
                        usuarioDAO.guardar(nuevoUsuario);
                        System.out.println("Usuario registrado con éxito.");
                        break;
                    case 4:
                        System.out.println("Consultar préstamos");
                        PrestamoEntity[] prestamos = prestamoDAO.consultarTodos();
                        if (prestamos.length == 0) {
                            System.out.println("No hay préstamos registrados.");
                        } else {
                            for (PrestamoEntity prestamo : prestamos) {
                                System.out.println(prestamo);
                            }
                        }
                        break;
                    case 5:
                        System.out.println("Registrar penalización");
                        System.out.println("Ingrese ID del usuario:");
                        int idUsuario = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Ingrese cantidad de libros fuera de plazo:");
                        int cantidadLibros = scanner.nextInt();
                        scanner.nextLine();
                        usuarioDAO.registrarPenalizacion(idUsuario, cantidadLibros);
                        System.out.println("Penalización registrada con éxito.");
                        break;
                    case 6:
                        System.out.println("Saliendo...");
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }

            } else if ("normal".equalsIgnoreCase(tipoUsuario)) {
                // Menú usuario normal
                System.out.println("\n--- MENÚ USUARIO NORMAL ---");
                System.out.println("1. Consultar mis préstamos");
                System.out.println("2. Registrar devolución");
                System.out.println("3. Salir");
                System.out.print("Elija una opción: ");
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer

                switch (opcion) {
                    case 1:
                        System.out.println("Consultar mis préstamos");
                        PrestamoEntity[] prestamosUsuario = prestamoDAO.consultarPorUsuarioId(usuario.getId());
                        if (prestamosUsuario.length == 0) {
                            System.out.println("No tiene préstamos activos.");
                        } else {
                            for (PrestamoEntity prestamo : prestamosUsuario) {
                                System.out.println(prestamo);
                            }
                        }
                        break;
                    case 2:
                        System.out.println("Registrar devolución");
                        System.out.println("Ingrese ID del préstamo:");
                        int idPrestamo = scanner.nextInt();
                        scanner.nextLine();
                        PrestamoEntity prestamo = prestamoDAO.buscarPorId(idPrestamo);
                        if (prestamo != null && prestamo.getUsuarioId() == usuario.getId()) {
                            prestamoDAO.registrarDevolucion(prestamo);
                            System.out.println("Devolución registrada con éxito.");
                        } else {
                            System.out.println("Préstamo no válido para este usuario.");
                        }
                        break;
                    case 3:
                        System.out.println("Saliendo...");
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            }
        }

        em.close();
        emf.close();
        scanner.close();
    }
}
