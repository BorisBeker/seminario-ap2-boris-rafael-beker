package AP4;
import java.util.Date;
import java.util.List;

public class Empleado extends Usuario {
    private NodoMeta cabeza;
    private int numeroDeMetas;
    private int calificacion;

    // Constructor
    public Empleado(String nombre, String email, String clave) {
        super(nombre, email, clave);
        this.cabeza = null;
        this.numeroDeMetas = 0;
        this.calificacion = 0;
    }

    // Getter y setter para la calificación
    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    // Método para agregar una meta
    public void agregarMeta(int id, String nombreMeta, String descripcion, Date fechaInicio, Date fechaFinEsperado) {
        boolean estado = false;
        int progreso = 0;
        Meta nuevaMeta = new Meta(id, nombreMeta, this.getNombre(), descripcion, fechaInicio, fechaFinEsperado, estado, progreso);
        NodoMeta nuevoNodo = new NodoMeta(nuevaMeta);
        if (cabeza == null) {
            cabeza = nuevoNodo;
        } else {
            NodoMeta actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevoNodo;
        }
        numeroDeMetas++;

        // Convertir java.util.Date a java.sql.Date
        java.sql.Date sqlFechaInicio = new java.sql.Date(fechaInicio.getTime());
        java.sql.Date sqlFechaFinEsperado = new java.sql.Date(fechaFinEsperado.getTime());

        // Llamar al método insertarMeta de ConexionBaseDeDatos
        ConexionBaseDeDatos.insertarMeta(id, nombreMeta, descripcion, sqlFechaInicio, sqlFechaFinEsperado, progreso, estado, this.getId());
    }

    // Método para obtener la lista de metas como un arreglo
    public Meta[] obtenerMetas() {
        List<Meta> metas = ConexionBaseDeDatos.obtenerMetasPorEmpleado(this.getId());
        return metas.toArray(new Meta[0]);
    }

    // Método para ordenar metas alfabéticamente usando burbuja con nodos
    public void ordenarMetas() {
        if (cabeza == null || cabeza.siguiente == null) {
            return;
        }
        boolean intercambiado;
        do {
            NodoMeta actual = cabeza;
            NodoMeta anterior = null;
            NodoMeta siguiente = cabeza.siguiente;
            intercambiado = false;

            while (siguiente != null) {
                if (actual.meta.getNombreMeta().compareTo(siguiente.meta.getNombreMeta()) > 0) {
                    intercambiado = true;
                    if (anterior != null) {
                        NodoMeta temp = siguiente.siguiente;
                        anterior.siguiente = siguiente;
                        siguiente.siguiente = actual;
                        actual.siguiente = temp;
                    } else {
                        NodoMeta temp = siguiente.siguiente;
                        cabeza = siguiente;
                        siguiente.siguiente = actual;
                        actual.siguiente = temp;
                    }
                    anterior = siguiente;
                    siguiente = actual.siguiente;
                } else {
                    anterior = actual;
                    actual = siguiente;
                    siguiente = siguiente.siguiente;
                }
            }
        } while (intercambiado);
    }

    // Método para actualizar una meta
    public void actualizarMeta(int id, String nombreMeta, String descripcion, Date fechaInicio, Date fechaFinEsperado, int progreso) {
        NodoMeta actual = cabeza;
        while (actual != null) {
            if (actual.meta.getId() == id) {
                actual.meta.setNombreMeta(nombreMeta);
                actual.meta.setDescripcion(descripcion);
                actual.meta.setFechaInicio(fechaInicio);
                actual.meta.setFechaFinEsperado(fechaFinEsperado);
                actual.meta.setProgreso(progreso);

                // Convertir java.util.Date a java.sql.Date
                java.sql.Date sqlFechaInicio = new java.sql.Date(fechaInicio.getTime());
                java.sql.Date sqlFechaFinEsperado = new java.sql.Date(fechaFinEsperado.getTime());
                
                ConexionBaseDeDatos.modificarMeta(id, nombreMeta, descripcion, sqlFechaInicio, sqlFechaFinEsperado, progreso, false);
                break;
            }
            actual = actual.siguiente;
        }
    }

    // Método para marcar una meta como finalizada
    public void marcarMetaFinalizada(int id) {
        NodoMeta actual = cabeza;
        while (actual != null) {
            if (actual.meta.getId() == id) {
                actual.meta.setEstado(true);
                actual.meta.setProgreso(100);

                // Llamar al método marcarMetaFinalizada de ConexionBaseDeDatos
                ConexionBaseDeDatos.marcarMetaFinalizada(id);
                break;
            }
            actual = actual.siguiente;
        }
    }

    // Método para solicitar asesoramiento al directivo
    public void solicitarAsesoramiento(Directivo directivo, String mensaje) {
        System.out.println("Mensaje al directivo " + directivo.getNombre() + ": " + mensaje);
        // Próximamente se agregará la posibilidad de enviar mails al directivo con el mensaje del empleado.
    }
}