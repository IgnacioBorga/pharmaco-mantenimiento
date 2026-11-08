package modelo;

public class Tecnico extends Usuario {

    public Tecnico(int idUsuario, String nombre, String apellido,
            String nombreDeUsuario, String contrasena, boolean activo) {
        super(idUsuario, nombre, apellido, nombreDeUsuario, contrasena, activo);
    }

    @Override
    public void mostrarRol() {
        System.out.println("Rol: Tecnico de mantenimiento");
    }
}