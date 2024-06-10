import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Directivo directivo;
    private static Usuario usuarioActual;
    private static Scanner scanner = new Scanner(System.in);
    private static List<Usuario> usuarios = new ArrayList<>();

    public static void main(String[] args) {
        // Crear una instancia de Directivo y agregarla a la lista de usuarios
        directivo = new Directivo(1, "Carlos Martinez", "carlos@example.com", "admin123");
        usuarios.add(directivo);

        while (true) {
            iniciarSesion();
            boolean salir = false;
            while (!salir) {
                if (usuarioActual instanceof Directivo) {
                    mostrarMenuDirectivo();
                } else {
                    mostrarMenuEmpleado();
                }
                int opcion = obtenerOpcion();

                if (usuarioActual instanceof Directivo) {
                    salir = manejarOpcionDirectivo(opcion);
                } else {
                    salir = manejarOpcionEmpleado(opcion);
                }
            }
        }
    }

    private static void mostrarMenuDirectivo() {
        System.out.println("\n1. Registrar empleado");
        System.out.println("2. Ver empleados");
        System.out.println("3. Ver metas de un empleado");
        System.out.println("4. Calificar empleado");
        System.out.println("5. Generar informe mensual");
        System.out.println("6. Cerrar sesión");
        System.out.print("Seleccione una opción: ");
    }

    private static void mostrarMenuEmpleado() {
        System.out.println("\n1. Ver metas");
        System.out.println("2. Agregar meta");
        System.out.println("3. Actualizar una meta");
        System.out.println("4. Marcar una meta como finalizada");
        System.out.println("5. Solicitar asesoramiento");
        System.out.println("6. Cerrar sesión");
        System.out.print("Seleccione una opción: ");
    }

    private static int obtenerOpcion() {
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, ingrese un número válido.");
            scanner.next();
        }
        int opcion = scanner.nextInt();
        scanner.nextLine();
        return opcion;
    }

    private static void iniciarSesion() {
        while (true) {
            System.out.print("Ingrese su email: ");
            String email = scanner.nextLine();
            System.out.print("Ingrese su clave: ");
            String clave = scanner.nextLine();

            boolean inicioExitoso = false;
            for (Usuario usuario : usuarios) {
                if (usuario.iniciarSesion(email, clave)) {
                    usuarioActual = usuario;
                    inicioExitoso = true;
                    if (usuario instanceof Directivo) {
                        System.out.println("Inicio de sesión exitoso como directivo.");
                    } else {
                        System.out.println("Inicio de sesión exitoso como empleado.");
                    }
                    break;
                }
            }

            if (inicioExitoso) {
                break;
            } else {
                System.out.println("Email o clave incorrectos. Intente de nuevo.");
            }
        }
    }

    private static boolean manejarOpcionDirectivo(int opcion) {
        switch (opcion) {
            case 1:
                registrarEmpleado();
                return false;
            case 2:
                verEmpleados();
                return false;
            case 3:
                verMetasEmpleado();
                return false;
            case 4:
                calificarEmpleado();
                return false;
            case 5:
                generarInformeMensual();
                return false;
            case 6:
                usuarioActual = null;
                return true;
            default:
                System.out.println("Opción no válida. Intente de nuevo.");
                return false;
        }
    }

    private static boolean manejarOpcionEmpleado(int opcion) {
        switch (opcion) {
            case 1:
                verMetas();
                return false;
            case 2:
                agregarMeta();
                return false;
            case 3:
                actualizarMeta();
                return false;
            case 4:
                marcarMetaFinalizada();
                return false;
            case 5:
                solicitarAsesoramiento();
                return false;
            case 6:
                usuarioActual = null;
                return true;
            default:
                System.out.println("Opción no válida. Intente de nuevo.");
                return false;
        }
    }

    private static void registrarEmpleado() {
        System.out.print("Ingrese el nombre del empleado: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el email del empleado: ");
        String email = scanner.nextLine();
        System.out.print("Ingrese la clave del empleado: ");
        String clave = scanner.nextLine();
        
        int id = (int) (Math.random() * (100000 - 1000 + 1)) + 1000;

        Empleado nuevoEmpleado = new Empleado(id, nombre, email, clave);
        usuarios.add(nuevoEmpleado);
        System.out.println("Empleado registrado: " + nuevoEmpleado.getNombre());
    }

    private static void verEmpleados() {
        for (Usuario usuario : usuarios) {
            if (usuario instanceof Empleado) {
                Empleado empleado = (Empleado) usuario;
                System.out.println("ID: " + empleado.getId() + ", Nombre: " + empleado.getNombre());
            }
        }
    }

    private static void verMetasEmpleado() {
        System.out.print("Ingrese el ID del empleado: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, ingrese un ID válido.");
            scanner.next();
        }
        int idEmpleado = scanner.nextInt();
        scanner.nextLine();

        for (Usuario usuario : usuarios) {
            if (usuario instanceof Empleado && usuario.getId() == idEmpleado) {
                Empleado empleado = (Empleado) usuario;
                Meta[] metas = empleado.obtenerMetas();
                for (Meta meta : metas) {
                    System.out.println("Meta: " + meta.getNombreMeta() + ", Descripción: " + meta.getDescripcion() +
                            ", Progreso: " + meta.getProgreso() + "%, Estado: " + (meta.isEstado() ? "Finalizada" : "No finalizada"));
                }
                return;
            }
        }
        System.out.println("Empleado no encontrado.");
    }

    private static void calificarEmpleado() {
        System.out.print("Ingrese el ID del empleado a calificar: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, ingrese un ID válido.");
            scanner.next();
        }
        int idEmpleado = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Ingrese la calificación del empleado: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, ingrese un número válido.");
            scanner.next();
        }
        int calificacion = scanner.nextInt();
        scanner.nextLine();

        for (Usuario usuario : usuarios) {
            if (usuario instanceof Empleado && usuario.getId() == idEmpleado) {
                Empleado empleado = (Empleado) usuario;
                empleado.setCalificacion(calificacion);
                System.out.println("Empleado calificado exitosamente.");
                return;
            }
        }
        System.out.println("Empleado no encontrado.");
    }

    private static void generarInformeMensual() {
        System.out.println("Generando informe mensual...");
        String informe = directivo.generarInformeMensual();
        System.out.println("Informe mensual:\n" + informe);
    }

    private static void verMetas() {
        Empleado empleado = (Empleado) usuarioActual;
        Meta[] metas = empleado.obtenerMetas();
        for (Meta meta : metas) {
            System.out.println("Meta: " + meta.getNombreMeta() + ", Descripción: " + meta.getDescripcion() +
                    ", Progreso: " + meta.getProgreso() + "%, Estado: " + (meta.isEstado() ? "Finalizada" : "No finalizada"));
        }
    }

    private static void agregarMeta() {
        Empleado empleado = (Empleado) usuarioActual;

        System.out.print("Ingrese el nombre de la meta: ");
        String nombreMeta = scanner.nextLine();
        System.out.print("Ingrese la descripción de la meta: ");
        String descripcion = scanner.nextLine();
        System.out.print("Ingrese la fecha de inicio (yyyy-MM-dd): ");
        String fechaInicioStr = scanner.nextLine();
        System.out.print("Ingrese la fecha de fin esperado (yyyy-MM-dd): ");
        String fechaFinEsperadoStr = scanner.nextLine();
        scanner.nextLine();

        int idMeta = (int) (Math.random() * (100000 - 1000 + 1)) + 1000;

        try {
            Date fechaInicio = new SimpleDateFormat("yyyy-MM-dd").parse(fechaInicioStr);
            Date fechaFinEsperado = new SimpleDateFormat("yyyy-MM-dd").parse(fechaFinEsperadoStr);
            empleado.agregarMeta(idMeta, nombreMeta, descripcion, fechaInicio, fechaFinEsperado);
            System.out.println("Meta agregada exitosamente.");
        } catch (ParseException e) {
            System.out.println("Formato de fecha no válido. Por favor, use el formato yyyy-MM-dd.");
        }
    }

    private static void actualizarMeta() {
        Empleado empleado = (Empleado) usuarioActual;

        System.out.print("Ingrese el ID de la meta a actualizar: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, ingrese un ID válido.");
            scanner.next();
        }
        int idMeta = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Ingrese el nuevo nombre de la meta: ");
        String nombreMeta = scanner.nextLine();
        System.out.print("Ingrese la nueva descripción de la meta: ");
        String descripcion = scanner.nextLine();
        System.out.print("Ingrese la nueva fecha de inicio (yyyy-MM-dd): ");
        String fechaInicioStr = scanner.nextLine();
        System.out.print("Ingrese la nueva fecha de fin esperado (yyyy-MM-dd): ");
        String fechaFinEsperadoStr = scanner.nextLine();
        System.out.print("Ingrese el nuevo progreso de la meta: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, ingrese un número válido.");
            scanner.next();
        }
        int progreso = scanner.nextInt();
        scanner.nextLine();

        try {
            Date fechaInicio = new SimpleDateFormat("yyyy-MM-dd").parse(fechaInicioStr);
            Date fechaFinEsperado = new SimpleDateFormat("yyyy-MM-dd").parse(fechaFinEsperadoStr);
            empleado.actualizarMeta(idMeta, nombreMeta, descripcion, fechaInicio, fechaFinEsperado, progreso);
            System.out.println("Meta actualizada exitosamente.");
        } catch (ParseException e) {
            System.out.println("Formato de fecha no válido. Por favor, use el formato yyyy-MM-dd.");
        }
    }

    private static void marcarMetaFinalizada() {
        Empleado empleado = (Empleado) usuarioActual;

        System.out.print("Ingrese el ID de la meta a marcar como finalizada: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, ingrese un ID válido.");
            scanner.next();
        }
        int idMeta = scanner.nextInt();
        scanner.nextLine();

        empleado.marcarMetaFinalizada(idMeta);
        System.out.println("Meta marcada como finalizada.");
    }

    private static void solicitarAsesoramiento() {
        Empleado empleado = (Empleado) usuarioActual;

        System.out.print("Ingrese el mensaje de solicitud de asesoramiento: ");
        String mensaje = scanner.nextLine();

        empleado.solicitarAsesoramiento(directivo, mensaje);
        System.out.println("Solicitud de asesoramiento enviada.");
    }
}