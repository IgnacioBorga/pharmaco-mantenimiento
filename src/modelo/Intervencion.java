package modelo;

import java.time.LocalDateTime;

public class Intervencion {

    private int idIntervencion;
    private LocalDateTime fecha;
    private String tareasRealizadas;
    private String observaciones;
    private OrdenTrabajo orden;
    private Usuario tecnico;

    public Intervencion(int idIntervencion,
            String tareasRealizadas,
            String observaciones,
            OrdenTrabajo orden,
            Usuario tecnico) {
        this.idIntervencion = idIntervencion;
        this.fecha = LocalDateTime.now();
        this.tareasRealizadas = tareasRealizadas;
        this.observaciones = observaciones;
        this.orden = orden;
        this.tecnico = tecnico;
    }

    public void mostrarDatos() {
        System.out.println(
                "Intervencion " + idIntervencion +
                        " | OT: " + orden.getNumero() +
                        " | Tecnico: " + tecnico.getNombre() + " " + tecnico.getApellido() +
                        " | Tareas: " + tareasRealizadas +
                        " | Observaciones: " + observaciones);
    }

    public String getTareasRealizadas() {
        return tareasRealizadas;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public OrdenTrabajo getOrden() {
        return orden;
    }

    public Usuario getTecnico() {
        return tecnico;
    }
}