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
}