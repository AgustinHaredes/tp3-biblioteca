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

    JButton btnSocio = new JButton("Agregar Socio");
    JButton btnLibro = new JButton("Agregar Libro");
    JButton btnModSocio = new JButton("Modificar Socio");
    JButton btnBajaSocio = new JButton("Dar de Baja Socio");
    JButton btnListarSocios = new JButton("Listar Socios");
    JButton btnListarLibros = new JButton("Listar Libros");
    JButton btnPrestamo = new JButton("Realizar Préstamo");
    JButton btnDevolucion = new JButton("Procesar Devolución");
    JButton btnListarPrestamos = new JButton("Listar Préstamos");
    JButton btnSolicitarCompra = new JButton("Solicitar Compra");
    JButton btnListarSolicitudes = new JButton("Listar Solicitudes");
    JButton btnSalir = new JButton("Salir");

    // Acciones
    btnSocio.addActionListener(e -> Ventanas.agregarSocio(biblioteca));
    btnLibro.addActionListener(e -> Ventanas.agregarLibro(biblioteca));
    btnModSocio.addActionListener(e -> Ventanas.modificarSocio(biblioteca));
    btnBajaSocio.addActionListener(e -> Ventanas.darDeBajaSocio(biblioteca));
    btnListarSocios.addActionListener(e -> Ventanas.listarSocios(biblioteca));
    btnListarLibros.addActionListener(e -> Ventanas.listarLibros(biblioteca));
    btnPrestamo.addActionListener(e -> Ventanas.realizarPrestamo(biblioteca));
    btnDevolucion.addActionListener(e -> Ventanas.procesarDevolucion(biblioteca));
    btnListarPrestamos.addActionListener(e -> Ventanas.listarPrestamosActivos(biblioteca));
    btnSolicitarCompra.addActionListener(e -> Ventanas.solicitarCompra(biblioteca));
    btnListarSolicitudes.addActionListener(e -> Ventanas.listarSolicitudes(biblioteca));

    btnSalir.addActionListener(e -> System.exit(0));

    panelBotones.add(btnSocio);
    panelBotones.add(btnLibro);
    panelBotones.add(btnModSocio);
    panelBotones.add(btnBajaSocio);
    panelBotones.add(btnListarSocios);
    panelBotones.add(btnListarLibros);
    panelBotones.add(btnPrestamo);
    panelBotones.add(btnDevolucion);
    panelBotones.add(btnListarPrestamos);
    panelBotones.add(btnSolicitarCompra);
    panelBotones.add(btnListarSolicitudes);
    panelBotones.add(btnSalir);
    panelBotones.add(btnSalir);

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