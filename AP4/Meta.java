package AP4;

import java.util.Date;

public class Meta {
    private int id;
    private String nombreMeta;
    private String nombreEmpleado;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFinEsperado;
    private boolean estado; // true para completada, false para no completada
    private int progreso; // 0 a 100

    // Constructor
    public Meta(int id, String nombreMeta, String nombreEmpleado, String descripcion, Date fechaInicio, Date fechaFinEsperado, boolean estado, int progreso) {
        this.id = id;
        this.nombreMeta = nombreMeta;
        this.nombreEmpleado = nombreEmpleado;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFinEsperado = fechaFinEsperado;
        this.estado = estado;
        this.progreso = progreso;
    }

    Meta(int id, String titulo, String descripcion, Date fechaInicio, Date fechaFin, boolean estado, int idEmpleado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreMeta() {
        return nombreMeta;
    }

    public void setNombreMeta(String nombreMeta) {
        this.nombreMeta = nombreMeta;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinEsperado() {
        return fechaFinEsperado;
    }

    public void setFechaFinEsperado(Date fechaFinEsperado) {
        this.fechaFinEsperado = fechaFinEsperado;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getProgreso() {
        return progreso;
    }

    public void setProgreso(int progreso) {
        this.progreso = progreso;
    }
}
