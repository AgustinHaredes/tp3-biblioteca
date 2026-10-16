package gestionBiblioteca;

import java.time.LocalDate;

/*Clase para la solicitud de compra de libros 
para la bilioteca
 */

public class SolicitudCompra {
    //Atributos de la clase
    private int idSolicitud;
    private LocalDate fechaSolicitud;
    private int cantidad;
    private String estado;
    private String observaciones;
    //Constructor propio de la clase
    public SolicitudCompra(int idSolicitud, LocalDate fechaSolicitud, int cantidad, String observaciones){
        this.idSolicitud = idSolicitud;
        this.fechaSolicitud = fechaSolicitud;
        this.cantidad = cantidad;
        this.estado = "Pendiente";
        this.observaciones = observaciones;
    }
    //Moidifica los estados de la solicitud a Aprobado o Rechazado
    public void aprobar(){
        this.estado = "Aprobado";
    }
    public void rechazado(){
        this.estado = "Rechazado";
    }
    //Metodo get y set de cada atributo
    public int getIdSolicitud(){return idSolicitud;}
    public void setIdSolicitud(int idSolicitud){ this.idSolicitud = idSolicitud;}

    public LocalDate getFechaSolicitud(){return fechaSolicitud;}
    public void setFechaSolicitud(LocalDate fechaSolicitud){this.fechaSolicitud = fechaSolicitud;}

    public int getCantidad(){return cantidad;}
    public void setCantidad(int cantidad){this.cantidad = cantidad;}

    public String getEstado(){return estado;}
    public void setEstado(String estado){this.estado = estado;}

    public String getObservaciones(){return observaciones;}
    public void setObservaciones(String observaciones){this.observaciones = observaciones;}

    @Override //Devuelve un texto ordenado
    public String toString(){
         return "Solicitud #" + idSolicitud +
               " | Fecha: " + fechaSolicitud +
               " | Cantidad: " + cantidad +
               " | Estado: " + estado +
               " | Obs: " + observaciones;
    }
}
