package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modelo.Equipo;

public class EquipoDAO {

    public static ArrayList<Equipo> listarEquipos() {

        ArrayList<Equipo> equipos = new ArrayList<>();

        String sql = """
                SELECT tag, nombre, descripcion, activo
                FROM equipo
                WHERE activo = TRUE
                """;

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
                ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {

                Equipo equipo = new Equipo(
                        resultado.getString("tag"),
                        resultado.getString("nombre"),
                        resultado.getString("descripcion"),
                        resultado.getBoolean("activo"));

                equipos.add(equipo);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar equipos: " + e.getMessage());
        }

        return equipos;
    }
}