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