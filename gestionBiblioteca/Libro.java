package gestionBiblioteca;

/*Clase abstracta base que defina la estructura general
y los atributos comunes de cualquier libro del sistema */

public abstract class Libro {
    //Atributos de la clase
    private String isbn;
    private String titulo;
    private String autor;
    private String editorial;
    private int año;
    private String categoria;
    private boolean disponible;

    // Constructor propio de la clase
    public Libro(String isbn, String titulo, String autor, String editorial, int año, String categoria) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.año = año;
        this.categoria = categoria;
        this.disponible = true;
    }

    // Metodo abstracto (
    public abstract double calcularPrecio();

    // Los metodos gets y sets de los atributos de la clase 
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }

    public int getAño() { return año; }
    public void setAño(int año) { this.año = año; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    // Método toString que devuelve texto ordenado
    @Override
    public String toString() {
        return "ISBN: " + isbn + " | Título: " + titulo + " | Autor: " + autor +
               " | Editorial: " + editorial + " | Año: " + año +
               " | Categoría: " + categoria + " | Disponible: " + (disponible ? "Sí" : "No");
    }
}