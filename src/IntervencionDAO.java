import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IntervencionDAO {

    public static boolean registrarIntervencion(Intervencion intervencion) {

        String sql = """
                INSERT INTO intervencion
                (tareas_realizadas, observaciones, numero_orden, id_tecnico)
                VALUES (?, ?, ?, ?)
                """;

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, intervencion.getTareasRealizadas());
            sentencia.setString(2, intervencion.getObservaciones());
            sentencia.setInt(3, intervencion.getOrden().getNumero());
            sentencia.setInt(4, intervencion.getTecnico().getIdUsuario());

            sentencia.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar intervencion: " + e.getMessage());
            return false;
        }
    }

    public static ArrayList<Intervencion> listarIntervenciones(
            ArrayList<OrdenTrabajo> ordenes,
            Usuario tecnico) {

        ArrayList<Intervencion> intervenciones = new ArrayList<>();

        String sql = """
                SELECT id_intervencion, tareas_realizadas,
                       observaciones, numero_orden
                FROM intervencion
                ORDER BY id_intervencion
                """;

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
                ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {

                OrdenTrabajo ordenEncontrada = null;

                for (OrdenTrabajo orden : ordenes) {
                    if (orden.getNumero() == resultado.getInt("numero_orden")) {

                        ordenEncontrada = orden;
                        break;
                    }
                }

                if (ordenEncontrada != null) {

                    Intervencion intervencion = new Intervencion(
                            resultado.getInt("id_intervencion"),
                            resultado.getString("tareas_realizadas"),
                            resultado.getString("observaciones"),
                            ordenEncontrada,
                            tecnico);

                    intervenciones.add(intervencion);
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al consultar intervenciones: " + e.getMessage());
        }

        return intervenciones;
    }
}