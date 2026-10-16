package gestionBiblioteca;

/*Subclase que representa los libro de produccion nacional
manejada por la moneda local (Pesos) */

public class LibroNacional extends Libro {
    //Atributo de la clase
     private double precio;

     //Metodo constructor propio para la clase Libro nacional
    public LibroNacional(String isbn, String titulo, String autor, String editorial, int año, String categoria, double precio) {
        super(isbn, titulo, autor, editorial, año, categoria);
        this.precio = precio;
    }
    //metodo Override de la clase Libro para calcular el precio
    @Override
    public double calcularPrecio() {
        return precio;
    }
    //metodos get y set de los atributos
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    @Override//Metodo que evuelve texto ordenado
    public String toString() {
        return super.toString() + " | Tipo: Nacional | Precio: $" + precio;
    }
}
