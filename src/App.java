import java.util.ArrayList;
import java.util.Scanner;

public class App {

        public static void main(String[] args) {

                try {
                        ConexionBD.conectar().close();
                        System.out.println("Conexion con MySQL exitosa.");
                } catch (Exception e) {
                        System.out.println("Error de conexion: " + e.getMessage());
                }

                Scanner scanner = new Scanner(System.in);

                Usuario solicitante = new Solicitante(
                                1,
                                "Juan",
                                "Perez",
                                "jperez",
                                "clave123",
                                true);

                Usuario tecnico = new Tecnico(
                                2,
                                "Carlos",
                                "Lopez",
                                "clopez",
                                "clave123",
                                true);

                ArrayList<Equipo> equipos = EquipoDAO.listarEquipos();

                ArrayList<SolicitudMantenimiento> solicitudes = SolicitudDAO.listarSolicitudes(equipos, solicitante);

                ArrayList<OrdenTrabajo> ordenes = OrdenTrabajoDAO.listarOrdenes(solicitudes, tecnico);

                ArrayList<Intervencion> intervenciones = IntervencionDAO.listarIntervenciones(ordenes, tecnico);

                int opcion = -1;

                do {
                        System.out.println();
                        System.out.println("===== PharmaCO - Gestion de Mantenimiento =====");
                        System.out.println("1. Listar equipos");
                        System.out.println("2. Buscar equipo por TAG");
                        System.out.println("3. Registrar solicitud");
                        System.out.println("4. Listar solicitudes");
                        System.out.println("5. Generar orden de trabajo");
                        System.out.println("6. Listar ordenes de trabajo");
                        System.out.println("7. Registrar intervencion");
                        System.out.println("8. Listar intervenciones");
                        System.out.println("9. Actualizar estado de OT");
                        System.out.println("10. Revisar OT");
                        System.out.println("0. Salir");
                        System.out.print("Seleccione una opcion: ");

                        try {
                                opcion = Integer.parseInt(scanner.nextLine());

                                switch (opcion) {

                                        case 1:
                                                listarEquipos(equipos);
                                                break;

                                        case 2:
                                                buscarEquipo(scanner, equipos);
                                                break;

                                        case 3:
                                                registrarSolicitud(scanner, equipos, solicitudes, solicitante);
                                                break;

                                        case 4:
                                                listarSolicitudes(solicitudes);
                                                break;

                                        case 5:
                                                generarOrden(scanner, solicitudes, ordenes, tecnico);
                                                break;

                                        case 6:
                                                listarOrdenes(ordenes);
                                                break;

                                        case 7:
                                                registrarIntervencion(scanner, ordenes, intervenciones, tecnico);
                                                break;

                                        case 8:
                                                listarIntervenciones(intervenciones);
                                                break;

                                        case 9:
                                                actualizarEstadoOrden(scanner, ordenes);
                                                break;

                                        case 10:
                                                revisarOrden(scanner, ordenes);
                                                break;

                                        case 0:
                                                System.out.println("Programa finalizado.");
                                                break;

                                        default:
                                                System.out.println("Opcion no valida.");
                                }

                        } catch (NumberFormatException e) {
                                System.out.println("Error: debe ingresar un numero.");
                        }

                } while (opcion != 0);

                scanner.close();
        }

        public static void listarEquipos(ArrayList<Equipo> equipos) {

                System.out.println();
                System.out.println("=== Equipos activos ===");

                for (Equipo equipo : equipos) {
                        if (equipo.isActivo()) {
                                equipo.mostrarDatos();
                        }
                }
        }

        public static void buscarEquipo(Scanner scanner, ArrayList<Equipo> equipos) {

                System.out.print("Ingrese el TAG del equipo: ");
                String tagBuscado = scanner.nextLine();

                boolean encontrado = false;

                for (Equipo equipo : equipos) {

                        if (equipo.getTag().equalsIgnoreCase(tagBuscado)) {
                                equipo.mostrarDatos();
                                encontrado = true;
                                break;
                        }
                }

                if (!encontrado) {
                        System.out.println("Equipo no encontrado.");
                }
        }

        public static void registrarSolicitud(
                        Scanner scanner,
                        ArrayList<Equipo> equipos,
                        ArrayList<SolicitudMantenimiento> solicitudes,
                        Usuario solicitante) {

                System.out.print("Ingrese el TAG del equipo: ");
                String tag = scanner.nextLine();

                Equipo equipoEncontrado = null;

                for (Equipo equipo : equipos) {
                        if (equipo.getTag().equalsIgnoreCase(tag) && equipo.isActivo()) {
                                equipoEncontrado = equipo;
                                break;
                        }
                }

                if (equipoEncontrado == null) {
                        System.out.println("Equipo inexistente o inactivo.");
                        return;
                }

                System.out.print("Ingrese la descripcion de la falla: ");
                String descripcion = scanner.nextLine();

                if (descripcion.isBlank()) {
                        System.out.println("La descripcion no puede estar vacia.");
                        return;
                }

                SolicitudMantenimiento solicitud = new SolicitudMantenimiento(
                                0,
                                descripcion,
                                equipoEncontrado,
                                solicitante);

                if (SolicitudDAO.registrarSolicitud(solicitud)) {
                        solicitudes.add(solicitud);
                        System.out.println("Solicitud registrada correctamente en MySQL.");
                } else {
                        System.out.println("No se pudo registrar la solicitud.");
                }
        }

        public static void listarSolicitudes(
                        ArrayList<SolicitudMantenimiento> solicitudes) {

                System.out.println();
                System.out.println("=== Solicitudes registradas ===");

                if (solicitudes.isEmpty()) {
                        System.out.println("No hay solicitudes registradas.");
                        return;
                }

                for (SolicitudMantenimiento solicitud : solicitudes) {
                        solicitud.mostrarDatos();
                }
        }

        public static void generarOrden(
                        Scanner scanner,
                        ArrayList<SolicitudMantenimiento> solicitudes,
                        ArrayList<OrdenTrabajo> ordenes,
                        Usuario tecnico) {

                if (solicitudes.isEmpty()) {
                        System.out.println("No hay solicitudes disponibles.");
                        return;
                }

                System.out.print("Ingrese el ID de la solicitud: ");

                try {
                        int idSolicitud = Integer.parseInt(scanner.nextLine());

                        SolicitudMantenimiento solicitudEncontrada = null;

                        for (SolicitudMantenimiento solicitud : solicitudes) {
                                if (solicitud.getIdSolicitud() == idSolicitud) {
                                        solicitudEncontrada = solicitud;
                                        break;
                                }
                        }

                        if (solicitudEncontrada == null) {
                                System.out.println("Solicitud no encontrada.");
                                return;
                        }

                        System.out.print("Ingrese la prioridad (Alta, Media o Baja): ");
                        String prioridad = scanner.nextLine();

                        if (!prioridad.equalsIgnoreCase("Alta")
                                        && !prioridad.equalsIgnoreCase("Media")
                                        && !prioridad.equalsIgnoreCase("Baja")) {

                                System.out.println("Prioridad no valida.");
                                return;
                        }

                        int nuevoNumero = ordenes.size() + 1;

                        OrdenTrabajo orden = new OrdenTrabajo(
                                        nuevoNumero,
                                        prioridad,
                                        solicitudEncontrada,
                                        tecnico);

                        if (OrdenTrabajoDAO.registrarOrden(orden)) {
                                ordenes.add(orden);
                                System.out.println("Orden de trabajo registrada correctamente en MySQL.");
                        } else {
                                System.out.println("No se pudo registrar la orden de trabajo.");
                        }

                } catch (NumberFormatException e) {
                        System.out.println("El ID de la solicitud debe ser numerico.");
                }
        }

        public static void listarOrdenes(ArrayList<OrdenTrabajo> ordenes) {

                System.out.println();
                System.out.println("=== Ordenes de trabajo ===");

                if (ordenes.isEmpty()) {
                        System.out.println("No hay ordenes registradas.");
                        return;
                }

                for (OrdenTrabajo orden : ordenes) {
                        orden.mostrarDatos();
                }
        }

        public static void registrarIntervencion(
                        Scanner scanner,
                        ArrayList<OrdenTrabajo> ordenes,
                        ArrayList<Intervencion> intervenciones,
                        Usuario tecnico) {

                if (ordenes.isEmpty()) {
                        System.out.println("No hay ordenes de trabajo disponibles.");
                        return;
                }

                System.out.print("Ingrese el numero de OT: ");

                try {
                        int numeroOrden = Integer.parseInt(scanner.nextLine());

                        OrdenTrabajo ordenEncontrada = null;

                        for (OrdenTrabajo orden : ordenes) {
                                if (orden.getNumero() == numeroOrden) {
                                        ordenEncontrada = orden;
                                        break;
                                }
                        }

                        if (ordenEncontrada == null) {
                                System.out.println("Orden de trabajo no encontrada.");
                                return;
                        }

                        System.out.print("Ingrese las tareas realizadas: ");
                        String tareas = scanner.nextLine();

                        if (tareas.isBlank()) {
                                System.out.println("Las tareas realizadas no pueden estar vacias.");
                                return;
                        }

                        System.out.print("Ingrese observaciones: ");
                        String observaciones = scanner.nextLine();

                        int nuevoId = intervenciones.size() + 1;

                        Intervencion intervencion = new Intervencion(
                                        nuevoId,
                                        tareas,
                                        observaciones,
                                        ordenEncontrada,
                                        tecnico);

                        if (IntervencionDAO.registrarIntervencion(intervencion)) {
                                intervenciones.add(intervencion);
                                System.out.println("Intervencion registrada correctamente en MySQL.");
                        } else {
                                System.out.println("No se pudo registrar la intervencion.");
                        }

                } catch (NumberFormatException e) {
                        System.out.println("El numero de OT debe ser numerico.");
                }
        }

        public static void listarIntervenciones(
                        ArrayList<Intervencion> intervenciones) {

                System.out.println();
                System.out.println("=== Intervenciones registradas ===");

                if (intervenciones.isEmpty()) {
                        System.out.println("No hay intervenciones registradas.");
                        return;
                }

                for (Intervencion intervencion : intervenciones) {
                        intervencion.mostrarDatos();
                }
        }

        public static void actualizarEstadoOrden(
                        Scanner scanner,
                        ArrayList<OrdenTrabajo> ordenes) {

                System.out.print("Ingrese el numero de OT: ");

                try {
                        int numero = Integer.parseInt(scanner.nextLine());

                        OrdenTrabajo ordenEncontrada = null;

                        for (OrdenTrabajo orden : ordenes) {
                                if (orden.getNumero() == numero) {
                                        ordenEncontrada = orden;
                                        break;
                                }
                        }

                        if (ordenEncontrada == null) {
                                System.out.println("Orden no encontrada.");
                                return;
                        }

                        System.out.println("1. Pasar a En ejecucion");
                        System.out.println("2. Pasar a Pendiente de revision");
                        System.out.print("Seleccione una opcion: ");

                        int opcion = Integer.parseInt(scanner.nextLine());

                        if (opcion == 1) {

                                if (OrdenTrabajoEstadoDAO.actualizarEstado(
                                                ordenEncontrada.getNumero(),
                                                "En ejecucion")) {

                                        ordenEncontrada.iniciarEjecucion();
                                        System.out.println("OT actualizada a En ejecucion.");

                                } else {
                                        System.out.println("No se pudo actualizar la OT.");
                                }

                        } else if (opcion == 2) {

                                if (OrdenTrabajoEstadoDAO.actualizarEstado(
                                                ordenEncontrada.getNumero(),
                                                "Pendiente de revision")) {

                                        ordenEncontrada.enviarARevision();
                                        System.out.println("OT actualizada a Pendiente de revision.");

                                } else {
                                        System.out.println("No se pudo actualizar la OT.");
                                }

                        } else {
                                System.out.println("Opcion no valida.");
                        }

                } catch (NumberFormatException e) {
                        System.out.println("Debe ingresar valores numericos.");
                }
        }

        public static void revisarOrden(
                        Scanner scanner,
                        ArrayList<OrdenTrabajo> ordenes) {

                System.out.print("Ingrese el numero de OT: ");

                try {
                        int numero = Integer.parseInt(scanner.nextLine());

                        OrdenTrabajo ordenEncontrada = null;

                        for (OrdenTrabajo orden : ordenes) {
                                if (orden.getNumero() == numero) {
                                        ordenEncontrada = orden;
                                        break;
                                }
                        }

                        if (ordenEncontrada == null) {
                                System.out.println("Orden no encontrada.");
                                return;
                        }

                        System.out.println("1. Aprobar y cerrar OT");
                        System.out.println("2. Rechazar y reactivar OT");
                        System.out.print("Seleccione una opcion: ");

                        int opcion = Integer.parseInt(scanner.nextLine());

                        if (opcion == 1) {

                                if (OrdenTrabajoEstadoDAO.cerrarOrden(
                                                ordenEncontrada.getNumero())) {

                                        ordenEncontrada.cerrar();
                                        System.out.println("OT cerrada correctamente.");

                                } else {
                                        System.out.println("No se pudo cerrar la OT.");
                                }

                        } else if (opcion == 2) {

                                if (OrdenTrabajoEstadoDAO.actualizarEstado(
                                                ordenEncontrada.getNumero(),
                                                "En ejecucion")) {

                                        ordenEncontrada.reactivar();
                                        System.out.println("OT reactivada correctamente.");

                                } else {
                                        System.out.println("No se pudo reactivar la OT.");
                                }

                        } else {
                                System.out.println("Opcion no valida.");
                        }

                } catch (NumberFormatException e) {
                        System.out.println("Debe ingresar valores numericos.");
                }
        }

}