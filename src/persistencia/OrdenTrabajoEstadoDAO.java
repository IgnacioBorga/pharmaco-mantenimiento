package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrdenTrabajoEstadoDAO {

    public static boolean actualizarEstado(int numeroOrden, String estado) {

        String sql = """
                UPDATE orden_trabajo
                SET estado = ?
                WHERE numero = ?
                """;

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, estado);
            sentencia.setInt(2, numeroOrden);

            return sentencia.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar estado: " + e.getMessage());
            return false;
        }
    }

    public static boolean cerrarOrden(int numeroOrden) {

        String sql = """
                UPDATE orden_trabajo
                SET estado = 'Cerrada',
                    fecha_cierre = CURRENT_TIMESTAMP
                WHERE numero = ?
                """;

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, numeroOrden);

            return sentencia.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al cerrar la OT: " + e.getMessage());
            return false;
        }
    }
}