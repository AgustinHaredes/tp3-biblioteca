package gestionBiblioteca;

import javax.swing.*;
import java.awt.*;

public class GestionBiblioteca extends JFrame {

    private static Biblioteca biblioteca = new Biblioteca("Dr. Antonio de la Cruz");

 public GestionBiblioteca() {
    setTitle("Sistema de Gestión Bibliotecaria - Menú Principal");
    setSize(800, 720);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());

    // Título
    JLabel titulo = new JLabel("Biblioteca Dr. Antonio de la Cruz", SwingConstants.CENTER);
    titulo.setFont(new Font("Arial", Font.BOLD, 22));
    titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
    add(titulo, BorderLayout.NORTH);

    // Panel de botones 
    JPanel panelBotones = new JPanel(new GridLayout(6, 2, 15, 15));
    panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
    //usamos un array para los botones
    String[] nombresBotones = {
        "Agregar Socio", "Agregar Libro",
        "Modificar Socio", "Dar de Baja Socio",
        "Listar Socios", "Listar Libros",
        "Realizar Préstamo", "Procesar Devolución",
        "Listar Préstamos", "Solicitar Compra",
        "Listar Solicitudes", "Salir"
    }; 
    //usamos el bucle for para generar los botones dinamicamente 
    for (int i = 0; i < nombresBotones.length; i++) {
        JButton boton = new JButton(nombresBotones[i]);
        
        // Asignamos la acción correspondiente según la posición en el arreglo
        switch (i) {
            case 0: boton.addActionListener(e -> Ventanas.agregarSocio(biblioteca)); break;
            case 1: boton.addActionListener(e -> Ventanas.agregarLibro(biblioteca)); break;
            case 2: boton.addActionListener(e -> Ventanas.modificarSocio(biblioteca)); break;
            case 3: boton.addActionListener(e -> Ventanas.darDeBajaSocio(biblioteca)); break;
            case 4: boton.addActionListener(e -> Ventanas.listarSocios(biblioteca)); break;
            case 5: boton.addActionListener(e -> Ventanas.listarLibros(biblioteca)); break;
            case 6: boton.addActionListener(e -> Ventanas.realizarPrestamo(biblioteca)); break;
            case 7: boton.addActionListener(e -> Ventanas.procesarDevolucion(biblioteca)); break;
            case 8: boton.addActionListener(e -> Ventanas.listarPrestamosActivos(biblioteca)); break;
            case 9: boton.addActionListener(e -> Ventanas.solicitarCompra(biblioteca)); break;
            case 10: boton.addActionListener(e -> Ventanas.listarSolicitudes(biblioteca)); break;
            case 11: boton.addActionListener(e -> System.exit(0)); break;
        }
    panelBotones.add(boton);
    }

    add(panelBotones, BorderLayout.CENTER);
}


    public static void main(String[] args) {
        biblioteca.cargarDatosDesdeBD();
        cargarDatosDePrueba();
        SwingUtilities.invokeLater(() -> new GestionBiblioteca().setVisible(true));
    }
    //Ejemplos de datos de socios
    private static void cargarDatosDePrueba() {
        if (biblioteca.buscarSocioPorDni("30123456") == null) {
            biblioteca.agregarSocio(new Socio(0, "Ana", "Gómez", "30123456",
                    "Av. Siempre Viva 123", "11-5555-1234", "ana@mail.com", "2026-01-15"));
            biblioteca.agregarSocio(new Socio(0, "Luis", "Pérez", "28987654",
                    "Calle Falsa 456", "11-5555-5678", "luis@mail.com", "2026-02-20"));
        }//Ejemplos de libros
        if (biblioteca.buscarLibroPorIsbn("978-8437600494") == null) {
            biblioteca.agregarLibro(new LibroNacional("978-8437600494", "Cien años de soledad",
                    "García Márquez", "Sudamericana", 1967, "Novela", 15000));
            biblioteca.agregarLibro(new LibroImportado("978-0061120084", "To Kill a Mockingbird",
                    "Harper Lee", "Harper", 1960, "Novela", 25, "EEUU"));
        }
    }
}
