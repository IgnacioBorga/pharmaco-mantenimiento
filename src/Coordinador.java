public class Coordinador extends Usuario {

    public Coordinador(int idUsuario, String nombre, String apellido,
            String nombreDeUsuario, String contrasena, boolean activo) {
        super(idUsuario, nombre, apellido, nombreDeUsuario, contrasena, activo);
    }

    @Override
    public void mostrarRol() {
        System.out.println("Rol: Coordinador de mantenimiento");
    }
}