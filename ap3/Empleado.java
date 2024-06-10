import java.util.Date;

public class Empleado extends Usuario {
    private NodoMeta cabeza;
    private int numeroDeMetas;
    private int calificacion;

    // Constructor
    public Empleado(int id, String nombre, String email, String clave) {
        super(id, nombre, email, clave);
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
    }

    // Método para obtener la lista de metas como un arreglo
    public Meta[] obtenerMetas() {
        Meta[] metas = new Meta[numeroDeMetas];
        NodoMeta actual = cabeza;
        int indice = 0;
        while (actual != null) {
            metas[indice++] = actual.meta;
            actual = actual.siguiente;
        }
        return metas;
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