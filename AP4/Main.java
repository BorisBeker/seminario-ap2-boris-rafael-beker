package AP4;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConexionBaseDeDatos.conectar();
        ConexionBaseDeDatos.crearTablas();

        Scanner scanner = new Scanner(System.in);
        Usuario usuarioActual = null;
        int opcion;

        do {
            System.out.println("Menú:");
            System.out.println("1. Crear Directivo");
            System.out.println("2. Crear Empleado");
            System.out.println("3. Iniciar Sesión");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir nueva línea

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el nombre del directivo: ");
                    String nombreDirectivo = scanner.nextLine();
                    System.out.print("Ingrese el email del directivo: ");
                    String emailDirectivo = scanner.nextLine();
                    System.out.print("Ingrese la contraseña del directivo: ");
                    String claveDirectivo = scanner.nextLine();
                    Directivo directivo = new Directivo(nombreDirectivo, emailDirectivo, claveDirectivo);
                    ConexionBaseDeDatos.insertarDirectivo(directivo);
                    break;
                case 2:
                    System.out.print("Ingrese el nombre del empleado: ");
                    String nombreEmpleado = scanner.nextLine();
                    System.out.print("Ingrese el email del empleado: ");
                    String emailEmpleado = scanner.nextLine();
                    System.out.print("Ingrese la contraseña del empleado: ");
                    String claveEmpleado = scanner.nextLine();
                    Empleado empleado = new Empleado(nombreEmpleado, emailEmpleado, claveEmpleado);
                    ConexionBaseDeDatos.insertarEmpleado(empleado);
                    break;
                case 3:
                    System.out.print("Ingrese el email: ");
                    String email = scanner.nextLine();
                    System.out.print("Ingrese la contraseña: ");
                    String clave = scanner.nextLine();

                    if (ConexionBaseDeDatos.existeUsuario(email, clave)) {
                        usuarioActual = ConexionBaseDeDatos.obtenerUsuario(email);

                        if (usuarioActual instanceof Directivo) {
                            mostrarMenuDirectivo((Directivo) usuarioActual, scanner);
                            
                        } else if (usuarioActual instanceof Empleado) {
                            mostrarMenuEmpleado((Empleado) usuarioActual, scanner);
                        }
                    } else {
                        System.out.println("Credenciales incorrectas.");
                    }
                    break;
                case 4:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        } while (opcion != 4);

        ConexionBaseDeDatos.cerrar();
        scanner.close();
    }

    public static void mostrarMenuDirectivo(Directivo directivo, Scanner scanner) {
        int opcion;

        do {
            System.out.println("\nMenú Directivo:");
            System.out.println("1. Registrar empleado");
            System.out.println("2. Ver empleados");
            System.out.println("3. Ver metas de un empleado");
            System.out.println("4. Calificar empleado");
            System.out.println("5. Generar informe mensual");
            System.out.println("6. Cerrar sesión");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir nueva línea

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el nombre del empleado: ");
                    String nombreEmpleado = scanner.nextLine();
                    System.out.print("Ingrese el email del empleado: ");
                    String emailEmpleado = scanner.nextLine();
                    System.out.print("Ingrese la contraseña del empleado: ");
                    String claveEmpleado = scanner.nextLine();
                    Empleado empleado = new Empleado(nombreEmpleado, emailEmpleado, claveEmpleado);
                    directivo.registrarEmpleado(empleado);
                    break;
                case 2:
                    List<Empleado> empleados = directivo.verEmpleados();
                    for (Empleado emp : empleados) {
                        System.out.println("ID: " + emp.getId());
                        System.out.println("Nombre: " + emp.getNombre());
                        System.out.println("Email: " + emp.getEmail());
                        System.out.println("Calificación: " + emp.getCalificacion());
                        System.out.println();
                    }
                    break;
                case 3:
                    System.out.print("Ingrese el ID del empleado: ");
                    int idEmpleado = scanner.nextInt();
                    scanner.nextLine(); // Consumir nueva línea
                    List<Meta> metas = directivo.verMetasDeEmpleado(idEmpleado);
                    for (Meta meta : metas) {
                        System.out.println("ID: " + meta.getId());
                        System.out.println("Descripción: " + meta.getDescripcion());
                        System.out.println("Fecha Inicio: " + meta.getFechaInicio());
                        System.out.println("Estado: " + meta.isEstado());
                        System.out.println();
                    }
                    break;
                case 4:
                    System.out.print("Ingrese el ID del empleado a calificar: ");
                    int idEmpleadoCalificar = scanner.nextInt();
                    scanner.nextLine(); // Consumir nueva línea
                    System.out.print("Ingrese la nueva calificación: ");
                    int nuevaCalificacion = scanner.nextInt();
                    scanner.nextLine(); // Consumir nueva línea
                    directivo.calificarEmpleado(idEmpleadoCalificar, nuevaCalificacion);
                    break;
                case 5:
                    directivo.generarInformeMensual();
                    break;
                case 6:
                    System.out.println("Cerrando sesión...");
                    return;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        } while (true);
    }

    public static void mostrarMenuEmpleado(Empleado empleado, Scanner scanner) {
        int opcion;

        do {
            System.out.println("\nMenú Empleado:");
            System.out.println("1. Ver metas");
            System.out.println("2. Agregar meta");
            System.out.println("3. Actualizar una meta");
            System.out.println("4. Marcar una meta como finalizada");
            System.out.println("5. Solicitar asesoramiento");
            System.out.println("6. Cerrar sesión");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir nueva línea

            switch (opcion) {
                case 1:
                    Meta[] metas = empleado.obtenerMetas();
                    for (Meta meta : metas) {
                        System.out.println("ID: " + meta.getId());
                        System.out.println("Descripción: " + meta.getDescripcion());
                        System.out.println("Fecha Inicio: " + meta.getFechaInicio());
                        System.out.println("Estado: " + meta.isEstado());
                        System.out.println();
                    }
                    break;
                case 2:
                    System.out.print("Ingrese el ID de la meta: ");
                    int idMeta = scanner.nextInt();
                    scanner.nextLine(); // Consumir nueva línea
                    System.out.print("Ingrese el nombre de la meta: ");
                    String nombreMeta = scanner.nextLine();
                    System.out.print("Ingrese la descripción de la meta: ");
                    String descripcionMeta = scanner.nextLine();
                    System.out.print("Ingrese la fecha de inicio (YYYY-MM-DD): ");
                    Date fechaInicio = Date.valueOf(scanner.nextLine());
                    System.out.print("Ingrese la fecha de fin esperada (YYYY-MM-DD): ");
                    Date fechaFinEsperado = Date.valueOf(scanner.nextLine());
                    empleado.agregarMeta(idMeta, nombreMeta, descripcionMeta, fechaInicio, fechaFinEsperado);
                    break;
                case 3:
                    System.out.print("Ingrese el ID de la meta a actualizar: ");
                    int idMetaActualizar = scanner.nextInt();
                    scanner.nextLine(); // Consumir nueva línea
                    System.out.print("Ingrese el nombre de la meta: ");
                    String nombreMetaActualizar = scanner.nextLine();
                    System.out.print("Ingrese la descripción de la meta: ");
                    String descripcionMetaActualizar = scanner.nextLine();
                    System.out.print("Ingrese la fecha de inicio (YYYY-MM-DD): ");
                    Date fechaInicioActualizar = Date.valueOf(scanner.nextLine());
                    System.out.print("Ingrese la fecha de fin esperada (YYYY-MM-DD): ");
                    Date fechaFinEsperadoActualizar = Date.valueOf(scanner.nextLine());
                    System.out.print("Ingrese el progreso: ");
                    int progreso = scanner.nextInt();
                    scanner.nextLine(); // Consumir nueva línea
                    empleado.actualizarMeta(idMetaActualizar, nombreMetaActualizar, descripcionMetaActualizar, fechaInicioActualizar, fechaFinEsperadoActualizar, progreso);
                    break;
                case 4:
                    System.out.print("Ingrese el ID de la meta a marcar como finalizada: ");
                    int idMetaFinalizar = scanner.nextInt();
                    scanner.nextLine(); // Consumir nueva línea
                    empleado.marcarMetaFinalizada(idMetaFinalizar);
                    break;
                case 5:
                    System.out.print("Ingrese el nombre del directivo: ");
                    String nombreDirectivo = scanner.nextLine();
                    Directivo directivo = (Directivo) ConexionBaseDeDatos.obtenerUsuario(nombreDirectivo);
                    if (directivo != null) {
                        System.out.print("Ingrese el mensaje: ");
                        String mensaje = scanner.nextLine();
                        empleado.solicitarAsesoramiento(directivo, mensaje);
                    } else {
                        System.out.println("Directivo no encontrado.");
                    }
                    break;
                case 6:
                    System.out.println("Cerrando sesión...");
                    return;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        } while (true);
    }
}