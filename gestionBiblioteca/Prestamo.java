package gestionBiblioteca;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*Clase que controla prestamos de libros realizado a un socio y controla
las fechas de entrega, el estado del préstamo y las multas si las hay. */

public class Prestamo {
    //Atributos de la clase
    private int idPrestamo;
    private Socio socio;
    private List<Libro> libros;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEstimada;
    private LocalDate fechaDevolucionReal;
    private String estado;
    private Multa multa;
    private static final int DIAS_PRESTAMO = 15;
    
    //Constructor propio del la Clase
    public Prestamo(int idPrestamo, Socio socio, LocalDate fechaPrestamo){
        this.idPrestamo = idPrestamo;
        this.socio = socio;
        this.libros = new ArrayList<>();
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionEstimada = fechaPrestamo.plusDays(DIAS_PRESTAMO);
        this.fechaDevolucionReal = null;
        this.estado = "activo";
        this.multa = null;
    }
    //Agrega un nuevo libro a la lista si existe y esta disponble
    public void agregarLibro(Libro libro){
        if(libro != null && libro.isDisponible()){
            libros.add(libro);
            libro.setDisponible(false);

        }
    }
    //Finaliza el prestamo y calcula la multa si las hay
    public void finalizar(LocalDate fechaDevolucion){
        this.fechaDevolucionReal = fechaDevolucion;
        this.estado = "finalizado";
        //calcula la muta despues de la fecha estimada
      if (fechaDevolucion.isAfter(fechaDevolucionEstimada)) {
        this.multa = new Multa(idPrestamo, this.fechaPrestamo, fechaDevolucion);
        socio.acumularMultas(this.multa.getMonto());
    }
        //MArca los libros como disponibles nuevamente
        for(Libro libro : libros){
            libro.setDisponible(true);
        }

        try (var con = ConexionBD.obtenerConexion()) {
        //Actualizar el préstamo en la base de datos, cambiando la fecha y el estado
            String sqlPrestamo = "UPDATE prestamos SET fecha_devolucion_real = ?, estado = ? WHERE id_prestamo = ?";
            try (var ps = con.prepareStatement(sqlPrestamo)) {
                ps.setString(1, fechaDevolucion.toString());
                ps.setString(2, this.estado);
                ps.setInt(3, this.idPrestamo);
                ps.executeUpdate();
            }

            //Libera cada libros asociado al prestamo y los marca como disponibles
            String sqlLibro = "UPDATE libros SET disponible = TRUE WHERE isbn = ?";
            for (Libro l : libros) {
                try (var ps = con.prepareStatement(sqlLibro)) {
                    ps.setString(1, l.getIsbn());
                    ps.executeUpdate();
                }
            }
            //Si existe una mula la guarda en la BD
            if (this.multa != null) {
                String sqlMulta = "INSERT INTO multas (id_prestamo, monto, dias_demora, fecha_generacion, pagada) VALUES (?, ?, ?, ?, ?)";
                try (var ps = con.prepareStatement(sqlMulta)) {
                    ps.setInt(1, this.idPrestamo);
                    ps.setDouble(2, this.multa.getMonto());
                    ps.setInt(3, this.multa.getDiasDemora());
                    ps.setString(4, fechaDevolucion.toString());
                    ps.setBoolean(5, false);//Se crea como no pagada
                    ps.executeUpdate();
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al finalizar préstamo: " + e.getMessage());//Muestra mensaje de error
        }
    }
    
    //Verifica prestamo esta vencido
    public boolean estaVencido(LocalDate fechaActual) {
        if (estado.equals("finalizado")) return false;
        return fechaActual.isAfter(fechaDevolucionEstimada);
    }
    //Devuelve la lista de libros
    public boolean tieneLibros() {
        return !libros.isEmpty();
    }

    // Metodos gets y sets de los atributos de la clase
    public int getIdPrestamo() { return idPrestamo; }
    public void setIdPrestamo(int idPrestamo) { this.idPrestamo = idPrestamo; }

    public Socio getSocio() { return socio; }
    public void setSocio(Socio socio) { this.socio = socio; }

    public List<Libro> getLibros() { return libros; }

    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }

    public LocalDate getFechaDevolucionEstimada() { return fechaDevolucionEstimada; }
    public void setFechaDevolucionEstimada(LocalDate fecha) { this.fechaDevolucionEstimada = fecha; }

    public LocalDate getFechaDevolucionReal() { return fechaDevolucionReal; }
    public void setFechaDevolucionReal(LocalDate fecha) { this.fechaDevolucionReal = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Multa getMulta() { return multa; }
    public void setMulta(Multa multa) { this.multa = multa; }

    @Override//Metodo que muestra un mensaje ordenado
    public String toString() {
        return "Préstamo #" + idPrestamo +
               " | Socio: " + socio.getApellido() + ", " + socio.getNombre() +
               " | Libros: " + libros.size() +
               " | Fecha préstamo: " + fechaPrestamo +
               " | Devolución estimada: " + fechaDevolucionEstimada +
               " | Estado: " + estado +
               " | Multa: $" + (multa != null ? multa.getMonto() : 0);
    }
}