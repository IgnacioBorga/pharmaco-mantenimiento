public abstract class Usuario {

    private int idUsuario;
    private String nombre;
    private String apellido;
    private String nombreDeUsuario;
    private String contrasena;
    private boolean activo;

    public Usuario(int idUsuario, String nombre, String apellido,
            String nombreDeUsuario, String contrasena, boolean activo) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreDeUsuario = nombreDeUsuario;
        this.contrasena = contrasena;
        this.activo = activo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNombreDeUsuario() {
        return nombreDeUsuario;
    }

    public boolean isActivo() {
        return activo;
    }

    public boolean autenticar(String usuario, String clave) {
        return activo
                && nombreDeUsuario.equals(usuario)
                && contrasena.equals(clave);
    }

    public abstract void mostrarRol();
}