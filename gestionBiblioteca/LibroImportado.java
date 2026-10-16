package gestionBiblioteca;

/*Sub clase para representa los libros importados de extranjero
con precio en dolares y pais de origen */

public class LibroImportado extends Libro {
    //Atributos de la clase
     private double precioDolar;
    private String paisOrigen;
    private static final double TIPO_CAMBIO = 1500.0; // Ejemplo: 1 USD = $1500

    //Metodo constructor Propio para la clase Libro importado
    public LibroImportado(String isbn, String titulo, String autor, String editorial, int año, String categoria, double precioDolar, String paisOrigen) {
        super(isbn, titulo, autor, editorial, año, categoria);
        this.precioDolar = precioDolar;
        this.paisOrigen = paisOrigen;
    }
    //metodo Override de la clase Libro para calcular el precio
    @Override
    public double calcularPrecio() {
        return precioDolar * TIPO_CAMBIO;
    }

    //Metodos Get y Set de los atributos de la clase
    public double getPrecioDolar() { return precioDolar; }
    public void setPrecioDolar(double precioDolar) { this.precioDolar = precioDolar; }

    public String getPaisOrigen() { return paisOrigen; }
    public void setPaisOrigen(String paisOrigen) { this.paisOrigen = paisOrigen; }

    @Override//Metodo que devuelve un texto ordenado
    public String toString() {
        return super.toString() + " | Tipo: Importado | Precio USD: " + precioDolar +
               " | País: " + paisOrigen + " | Precio en pesos: $" + calcularPrecio();
    }
}
