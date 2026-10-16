package gestionBiblioteca;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/*Clase que genera una multa por la demora en la devolución de un préstamo
calcula los días de retraso, el monto total a pagar y su estado */

public class Multa {
    //Atributos de la clase
    private int idMulta;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private int diasDemora;
    private double monto;
    private boolean pagada;
    private static final double MULTA_POR_DIA = 500.0;//Tarifa por retraso
    //Metodo constructos priopio de la clase
    public Multa(int idMulta, LocalDate fechaPrestamo, LocalDate fechaDevolucion) {
        this.idMulta = idMulta;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.diasDemora = calcularDiasDemora();
        this.monto = calcularMonto();
        this.pagada = false;
    }
    //calcula los dias de demora pasado el plazo
    private int calcularDiasDemora() {
        long dias = ChronoUnit.DAYS.between(fechaPrestamo, fechaDevolucion);
        long diasValidos = dias - 15; // 15 días de plazo
        return diasValidos > 0 ? (int) diasValidos : 0;
    }
    //calcula el monto total por dias de demara
    private double calcularMonto() {
        return diasDemora * MULTA_POR_DIA;
    }
    //cambia el estado de la multa
    public void pagar() {
        this.pagada = true;
    }

    // Metodos gets y sets de los atributos
    public int getIdMulta() { return idMulta; }
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public int getDiasDemora() { return diasDemora; }
    public double getMonto() { return monto; }
    public boolean isPagada() { return pagada; }

    @Override//Devuelve texto ordenado
    public String toString() {
        return "Multa #" + idMulta + " | Días demora: " + diasDemora +
               " | Monto: $" + monto + " | Pagada: " + (pagada ? "Sí" : "No");
    }
}