package Biblioteca;

import java.util.Scanner;
import jakarta.persistence.EntityManager;

public class Login {

    private static UsuarioDAO usuarioDAO = new UsuarioDAO();  // Asegúrate de tener un DAO de usuario

    public static UsuarioEntity login(EntityManager em) {
        Scanner scanner = new Scanner(System.in);

        // Solicitar usuario y contraseña
        System.out.print("Ingrese su DNI: ");
        String dni = scanner.nextLine();

        System.out.print("Ingrese su contraseña: ");
        String password = scanner.nextLine();

        // Comprobar si el usuario existe
        UsuarioEntity usuario = usuarioDAO.obtenerPorDniYContrasena(dni, password);

        if (usuario == null) {
            System.out.println("Datos incorrectos. Intente nuevamente.");
            return null;  // Si no es correcto, regresamos null
        } else {
            System.out.println("¡Bienvenido, " + usuario.getNombre() + "!");
            return usuario;  // Regresamos el usuario si está autenticado
        }
    }
}
