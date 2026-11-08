package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import modelo.Equipo;
import modelo.SolicitudMantenimiento;
import modelo.Usuario;

public class SolicitudDAO {

    public static boolean registrarSolicitud(SolicitudMantenimiento solicitud) {

        String sql = """
                INSERT INTO solicitud_mantenimiento
                (descripcion, estado, id_usuario, tag_equipo)
                VALUES (?, ?, ?, ?)
                """;

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS)) {

            sentencia.setString(1, solicitud.getDescripcion());
            sentencia.setString(2, solicitud.getEstado());
            sentencia.setInt(3, solicitud.getSolicitante().getIdUsuario());
            sentencia.setString(4, solicitud.getEquipo().getTag());

            sentencia.executeUpdate();

            try (ResultSet claves = sentencia.getGeneratedKeys()) {
                if (claves.next()) {
                    solicitud.setIdSolicitud(claves.getInt(1));
                }
            }

            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar solicitud: " + e.getMessage());
            return false;
        }
    }

    public static ArrayList<SolicitudMantenimiento> listarSolicitudes(
            ArrayList<Equipo> equipos,
            Usuario solicitante) {

        ArrayList<SolicitudMantenimiento> solicitudes = new ArrayList<>();

        String sql = """
                SELECT id_solicitud, descripcion, estado, tag_equipo
                FROM solicitud_mantenimiento
                ORDER BY id_solicitud
                """;

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
                ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {

                Equipo equipoEncontrado = null;

                for (Equipo equipo : equipos) {
                    if (equipo.getTag().equals(resultado.getString("tag_equipo"))) {
                        equipoEncontrado = equipo;
                        break;
                    }
                }

                if (equipoEncontrado != null) {

                    SolicitudMantenimiento solicitud = new SolicitudMantenimiento(
                            resultado.getInt("id_solicitud"),
                            resultado.getString("descripcion"),
                            equipoEncontrado,
                            solicitante);

                    solicitud.setEstado(resultado.getString("estado"));

                    solicitudes.add(solicitud);
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al consultar solicitudes: " + e.getMessage());
        }

        return solicitudes;
    }

    public static boolean actualizarEstado(int idSolicitud, String estado) {

        String sql = """
                UPDATE solicitud_mantenimiento
                SET estado = ?
                WHERE id_solicitud = ?
                """;

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, estado);
            sentencia.setInt(2, idSolicitud);

            return sentencia.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Error al actualizar solicitud: " + e.getMessage());
            return false;
        }
    }
}