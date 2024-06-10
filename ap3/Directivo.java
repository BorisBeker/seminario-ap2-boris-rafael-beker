import java.util.Date;

public class Directivo extends Usuario {
    private NodoEmpleado cabeza;
    private int numeroDeEmpleados;

    // Constructor
    public Directivo(int id, String nombre, String email, String clave) {
        super(id, nombre, email, clave);
        this.cabeza = null;
        this.numeroDeEmpleados = 0;
    }

    // Método para registrar un usuario
    public Usuario registrarUsuario(String nombre, String email, String clave, boolean rol) {
        int id = (int) (Math.random() * 1000); // Generar un ID aleatorio para el ejemplo
        if (rol) {
            // Si rol es true, crear un Directivo
            Directivo directivo = new Directivo(id, nombre, email, clave);
            return directivo;
        } else {
            // Si rol es false, crear un Empleado
            Empleado empleado = new Empleado(id, nombre, email, clave);
            agregarEmpleado(empleado);
            return empleado;
        }
    }

    // Método para agregar un empleado a la lista enlazada
    private void agregarEmpleado(Empleado empleado) {
        NodoEmpleado nuevoNodo = new NodoEmpleado(empleado);
        if (cabeza == null) {
            cabeza = nuevoNodo;
        } else {
            NodoEmpleado actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevoNodo;
        }
        numeroDeEmpleados++;
    }

    // Método para visualizar empleados
    public Empleado[] obtenerEmpleados() {
        Empleado[] empleados = new Empleado[numeroDeEmpleados];
        NodoEmpleado actual = cabeza;
        int indice = 0;
        while (actual != null) {
            empleados[indice++] = actual.empleado;
            actual = actual.siguiente;
        }
        return empleados;
    }

    // Método para visualizar las metas de un empleado seleccionado
    public Meta[] obtenerMetasDeEmpleado(int idEmpleado) {
        NodoEmpleado actual = cabeza;
        while (actual != null) {
            if (actual.empleado.getId() == idEmpleado) {
                return actual.empleado.obtenerMetas();
            }
            actual = actual.siguiente;
        }
        return null;
    }

    // Método para calificar a un empleado
    public void calificarEmpleado(int idEmpleado, int calificacion) {
        NodoEmpleado actual = cabeza;
        while (actual != null) {
            if (actual.empleado.getId() == idEmpleado) {
                actual.empleado.setCalificacion(calificacion);
            }
            actual = actual.siguiente;
        }
    }

    // Método para generar un informe de las metas de todos los empleados en el último mes
    public String generarInformeMensual() {
        StringBuilder informe = new StringBuilder();
        Date hoy = new Date();
        informe.append("Informe de metas del último mes:\n");
        NodoEmpleado actual = cabeza;
        while (actual != null) {
            Empleado empleado = actual.empleado;
            Meta[] metas = empleado.obtenerMetas();
            for (Meta meta : metas) {
                long diffInMillies = Math.abs(hoy.getTime() - meta.getFechaInicio().getTime());
                long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);
                if (diffInDays <= 30) {
                    informe.append("Empleado: ").append(meta.getNombreEmpleado()).append(", Meta: ").append(meta.getNombreMeta())
                            .append(", Progreso: ").append(meta.getProgreso()).append("%, Estado: ")
                            .append(meta.isEstado() ? "Finalizada" : "No finalizada").append("\n");
                }
            }
            actual = actual.siguiente;
        }
        return informe.toString();
    }
}