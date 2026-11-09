package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestiona la conexión entre la aplicación Java y la base de datos MySQL.
 * Las credenciales se obtienen desde variables de entorno.
 */
public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/pharmaco_mantenimiento";

    private static final String USUARIO = System.getenv("PHARMACO_DB_USER");
    private static final String CONTRASENA = System.getenv("PHARMACO_DB_PASSWORD");

    public static Connection conectar() throws SQLException {

        return DriverManager.getConnection(
                URL,
                USUARIO,
                CONTRASENA);
    }
}