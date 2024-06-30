package AP4;

import java.util.List;

public class Directivo extends Usuario {

    public Directivo(String nombre, String email, String clave) {
        super(nombre, email, clave);
    }

    public void registrarEmpleado(Empleado empleado) {
        ConexionBaseDeDatos.insertarEmpleado(empleado);
    }

    public List<Empleado> verEmpleados() {
        return ConexionBaseDeDatos.obtenerEmpleados();
    }

    public List<Meta> verMetasDeEmpleado(int idEmpleado) {
        return ConexionBaseDeDatos.obtenerMetasPorEmpleado(idEmpleado);
    }

    public void calificarEmpleado(int idEmpleado, int nuevaCalificacion) {
        ConexionBaseDeDatos.actualizarCalificacionEmpleado(idEmpleado, nuevaCalificacion);
    }

    public void generarInformeMensual() {
        List<Meta> metas = ConexionBaseDeDatos.obtenerTodasLasMetas();
        
        System.out.println("Informe Mensual de Metas:");
        System.out.println("==========================");
        
        for (Meta meta : metas) {
            System.out.println("ID: " + meta.getId());
            System.out.println("Título: " + meta.getNombreMeta());
            System.out.println("Descripción: " + meta.getDescripcion());
            System.out.println("Fecha Inicio: " + meta.getFechaInicio());
            System.out.println("Fecha Fin: " + meta.getFechaFinEsperado());
            System.out.println("Estado: " + (meta.isEstado() ? "Completado" : "En progreso"));
            System.out.println("Progreso: " + meta.getProgreso() + "%");
            System.out.println("----------------------------------");
        }
    }
}