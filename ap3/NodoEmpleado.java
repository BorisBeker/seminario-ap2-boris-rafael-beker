public class NodoEmpleado {
    Empleado empleado;
    NodoEmpleado siguiente;

    public NodoEmpleado(Empleado empleado) {
        this.empleado = empleado;
        this.siguiente = null;
    }
}