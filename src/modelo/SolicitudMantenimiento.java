package modelo;

import java.time.LocalDateTime;

public class SolicitudMantenimiento {

    private int idSolicitud;
    private LocalDateTime fechaCreacion;
    private String descripcion;
    private String estado;
    private Equipo equipo;
    private Usuario solicitante;

    public SolicitudMantenimiento(int idSolicitud,
            String descripcion,
            Equipo equipo,
            Usuario solicitante) {
        this.idSolicitud = idSolicitud;
        this.fechaCreacion = LocalDateTime.now();
        this.descripcion = descripcion;
        this.estado = "Registrada";
        this.equipo = equipo;
        this.solicitante = solicitante;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public String getEstado() {
        return estado;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void mostrarDatos() {
        System.out.println(
                "Solicitud " + idSolicitud +
                        " | Equipo: " + equipo.getTag() +
                        " | Estado: " + estado +
                        " | Descripcion: " + descripcion);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Usuario getSolicitante() {
        return solicitante;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }
}