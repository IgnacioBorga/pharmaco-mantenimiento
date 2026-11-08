package modelo;

public class Equipo {

    private String tag;
    private String nombre;
    private String descripcion;
    private boolean activo;

    public Equipo(String tag, String nombre, String descripcion, boolean activo) {
        this.tag = tag;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    public String getTag() {
        return tag;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void mostrarDatos() {
        System.out.println(tag + " - " + nombre + " - " + descripcion);
    }
}