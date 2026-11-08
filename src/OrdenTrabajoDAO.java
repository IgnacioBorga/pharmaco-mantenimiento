import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrdenTrabajoDAO {

    public static boolean registrarOrden(OrdenTrabajo orden) {

        String sql = """
                INSERT INTO orden_trabajo
                (prioridad, estado, id_solicitud, id_tecnico)
                VALUES (?, ?, ?, ?)
                """;

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, orden.getPrioridad());
            sentencia.setString(2, orden.getEstado());
            sentencia.setInt(3, orden.getSolicitud().getIdSolicitud());
            sentencia.setInt(4, orden.getTecnico().getIdUsuario());

            sentencia.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar OT: " + e.getMessage());
            return false;
        }
    }

    public static ArrayList<OrdenTrabajo> listarOrdenes(
            ArrayList<SolicitudMantenimiento> solicitudes,
            Usuario tecnico) {

        ArrayList<OrdenTrabajo> ordenes = new ArrayList<>();

        String sql = """
                SELECT numero, prioridad, estado, id_solicitud
                FROM orden_trabajo
                ORDER BY numero
                """;

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
                ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {

                SolicitudMantenimiento solicitudEncontrada = null;

                for (SolicitudMantenimiento solicitud : solicitudes) {
                    if (solicitud.getIdSolicitud() == resultado.getInt("id_solicitud")) {

                        solicitudEncontrada = solicitud;
                        break;
                    }
                }

                if (solicitudEncontrada != null) {

                    OrdenTrabajo orden = new OrdenTrabajo(
                            resultado.getInt("numero"),
                            resultado.getString("prioridad"),
                            solicitudEncontrada,
                            tecnico);

                    orden.setEstado(resultado.getString("estado"));

                    ordenes.add(orden);
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al consultar ordenes: " + e.getMessage());
        }

        return ordenes;
    }
}