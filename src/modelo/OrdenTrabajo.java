package modelo;

import java.time.LocalDateTime;

public class OrdenTrabajo {

    private int numero;
    private LocalDateTime fechaCreacion;
    private String prioridad;
    private String estado;
    private SolicitudMantenimiento solicitud;
    private Usuario tecnico;

    public OrdenTrabajo(int numero,
            String prioridad,
            SolicitudMantenimiento solicitud,
            Usuario tecnico) {
        this.numero = numero;
        this.fechaCreacion = LocalDateTime.now();
        this.prioridad = prioridad;
        this.estado = "Asignada";
        this.solicitud = solicitud;
        this.tecnico = tecnico;
    }

    public int getNumero() {
        return numero;
    }

    public String getEstado() {
        return estado;
    }

    public void mostrarDatos() {
        System.out.println(
                "OT " + numero +
                        " | Solicitud: " + solicitud.getIdSolicitud() +
                        " | Equipo: " + solicitud.getEquipo().getTag() +
                        " | Prioridad: " + prioridad +
                        " | Estado: " + estado +
                        " | Tecnico: " + tecnico.getNombre() + " " + tecnico.getApellido());
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void iniciarEjecucion() {
        this.estado = "En ejecucion";
    }

    public void enviarARevision() {
        this.estado = "Pendiente de revision";
    }

    public void cerrar() {
        this.estado = "Cerrada";
    }

    public void reactivar() {
        this.estado = "En ejecucion";
    }

    public String getPrioridad() {
        return prioridad;
    }

    public SolicitudMantenimiento getSolicitud() {
        return solicitud;
    }

    public Usuario getTecnico() {
        return tecnico;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    // Valida que el cambio de estado respete el flujo definido para una OT
    public boolean puedeCambiarA(String nuevoEstado) {

        if (estado.equals("Asignada")
                && nuevoEstado.equals("En ejecución")) {
            return true;
        }

        if (estado.equals("En ejecución")
                && nuevoEstado.equals("Pendiente de revisión")) {
            return true;
        }

        if (estado.equals("Pendiente de revisión")
                && nuevoEstado.equals("Cerrada")) {
            return true;
        }

        if (estado.equals("Pendiente de revisión")
                && nuevoEstado.equals("En ejecución")) {
            return true;
        }

        return false;
    }

}