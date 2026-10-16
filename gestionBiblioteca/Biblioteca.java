package gestionBiblioteca;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 Clase principal que representa la Biblioteca, encargada de centralizar 
 y administrar la lógica de socios, libros, préstamos y solicitudes de compra.
 */

public class Biblioteca {
    //Atributos de la clase
    private String nombre;
    private List<Socio> socios;
    private List<Libro> libros;
    private List<Prestamo> prestamos;
    private List<SolicitudCompra> solicitudes;
    private int contadorSolicitudes;
    private int contadorPrestamos;

    //Constructores Propios
    public Biblioteca(String nombre){
        this.nombre = nombre;
        this.socios = new ArrayList<>();
        this.libros = new ArrayList<>();
        this.prestamos = new ArrayList<>();
        this.solicitudes = new ArrayList<>();
        this.contadorSolicitudes = 1;
        this.contadorPrestamos = 1;
    }
    // Gestion de los Socios
    public void agregarSocio(Socio socio){
        if( socio != null){
            
            // Guarda infromacion de los socios en la base de datos
        try (Connection con = ConexionBD.obtenerConexion()) {
            String sql = "INSERT INTO socios (nombre, apellido, dni, domicilio, telefono, email, fecha_registro, activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (var ps = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, socio.getNombre());
                ps.setString(2, socio.getApellido());
                ps.setString(3, socio.getDni());
                ps.setString(4, socio.getDomicilio());
                ps.setString(5, socio.getTelefono());
                ps.setString(6, socio.getEmail());
                ps.setString(7, socio.getFechaRegistro());
                ps.setBoolean(8, socio.isActivo());
                ps.executeUpdate();

                //Obtener el ID de MySQL
                try (var rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        socio.setIdSocio(rs.getInt(1));
                    }
                }
            }
                socios.add(socio);
            } catch (SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error al guardar socio: " + e.getMessage());
            }
        }
    }
    //busca socios por medio del DNI
    public Socio buscarSocioPorDni(String dni){
        for(Socio s : socios){
            if(s.getDni().equals(dni)){
                return s;
            }
        }
        return null;
    }
    // Modifica infromacion del socio
public boolean modificarSocio(Socio socioModificado) {
    for (int i = 0; i < socios.size(); i++) {
        if (socios.get(i).getDni().equals(socioModificado.getDni())) {
            socios.set(i, socioModificado);
            //Se concta a la BD y modifica los dato del socio
            try (Connection con = ConexionBD.obtenerConexion()) {
                String sql = "UPDATE socios SET nombre = ?, apellido = ?, domicilio = ?, telefono = ?, email = ? WHERE dni = ?";
                    try (var ps = con.prepareStatement(sql)) {
                        ps.setString(1, socioModificado.getNombre());
                        ps.setString(2, socioModificado.getApellido());
                        ps.setString(3, socioModificado.getDomicilio());
                        ps.setString(4, socioModificado.getTelefono());
                        ps.setString(5, socioModificado.getEmail());
                        ps.setString(6, socioModificado.getDni());
                        ps.executeUpdate();
                    }
                } catch (SQLException e) {
                    System.out.println("Error al modificar socio: " + e.getMessage());//Muestra mensaje de error
                }

                return true;
            }
        }
        return false;
    }
    // Da de baja un socio 
    public boolean darDeBajaSocio(String dni) {
    Socio socio = buscarSocioPorDni(dni);
    if (socio == null) {
        return false;
    }
    // Verificar que el socio no tenga préstamos activos
    for (Prestamo p : prestamos) {
        if (p.getSocio().getDni().equals(dni) && p.getEstado().equalsIgnoreCase("activo")) {
            return false; // No se puede dar de baja si tiene préstamos activos
        }
    }
    socio.darDeBaja();
        //Se conecta a la BD y da de baja
        try (Connection con = ConexionBD.obtenerConexion()) {
            String sql = "UPDATE socios SET activo = FALSE WHERE dni = ?";
            try (var ps = con.prepareStatement(sql)) {
                ps.setString(1, dni);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error al dar de baja socio: " + e.getMessage());//Muestra mensaje de error
        }

        return true;
    }
    public List<Socio> getSocios(){
        return socios;
    }
    //muestra la lista de socios y los no registrados
    public void listarSocios(){
        if(socios.isEmpty()){
            System.out.println("No hay socio Registrado");
            return;
        } System.out.println("Lista de Socios: ");
        for(Socio s : socios){
            System.out.println(s);
        }
    }
    //========================
    //Gestion de los Libros
    public void agregarLibro(Libro libro) {
    if (libro != null) {
        libros.add(libro);
        //Se conecta a la base de datos
        try (Connection con = ConexionBD.obtenerConexion()) {
            String sql = "INSERT INTO libros (titulo, autor, editorial, año, isbn, categoria, disponible) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (var ps = con.prepareStatement(sql)) {
                ps.setString(1, libro.getTitulo());
                ps.setString(2, libro.getAutor());
                ps.setString(3, libro.getEditorial());
                ps.setInt(4, libro.getAño());
                ps.setString(5, libro.getIsbn());
                ps.setString(6, libro.getCategoria());
                ps.setBoolean(7, libro.isDisponible());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error al guardar libro: " + e.getMessage());//Mensaje de error
        }
    }
}
    //busca los libros por ISBN
    public Libro buscarLibroPorIsbn(String isbn) {
        for (Libro l : libros) {
            if (l.getIsbn().equals(isbn)) {
                return l;
            }
        }
        return null;
    }
    //retorna la lsita de libros
    public List<Libro> getLibros() {
        return libros;
    }
    //busca los libros disponibles
    public List<Libro> getLibrosDisponibles() {
        List<Libro> disponibles = new ArrayList<>();
        for (Libro l : libros) {
            if (l.isDisponible()) {
                disponibles.add(l);
            }
        }
        return disponibles;
    }
    //muestra la lista de libros disponibles y los no registrados
    public void listarLibros() {
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados");
            return;
        }
        System.out.println("Catalogo de Libros: ");
        for (Libro l : libros) {
            System.out.println(l);
        }
    }
    //==========================
    // Gestion de los Prestamos
    public Prestamo registrarPrestamo(Socio socio, List<Libro> librosPrestados) {    
    //Valida que el socio y la lista de libros no estén vacíos o nulos
    if (socio == null || librosPrestados == null || librosPrestados.isEmpty()) {        
        return null;    
    }    
    //Comprueba que el socio esté activo
    if (!socio.isActivo()) {        
        System.out.println("El socio no está activo");        
        return null;    
    }    
    if (socio.tieneMultasPendientes()) { //Comprueba que el socio no tenga multas sin pagar
        System.out.println("El socio tiene multas pendientes");        
        return null;    
    }    
    Prestamo p = new Prestamo(contadorPrestamos++, socio, java.time.LocalDate.now());    
    for (Libro l : librosPrestados) {        
        p.agregarLibro(l);    
    }    
    prestamos.add(p);    

    //Guarda el préstamo en la base de datos
    try (Connection con = ConexionBD.obtenerConexion()) {        
        // Inserta el registro a la BD
        String sqlPrestamo = "INSERT INTO prestamos (id_socio, fecha_prestamo, fecha_devolucion_estimada, estado) VALUES (?, ?, ?, ?)";        
        int idPrestamoGenerado = -1;        
        try (var ps = con.prepareStatement(sqlPrestamo, java.sql.Statement.RETURN_GENERATED_KEYS)) {            
            ps.setInt(1, socio.getIdSocio());            
            ps.setString(2, p.getFechaPrestamo().toString());            
            ps.setString(3, p.getFechaDevolucionEstimada().toString());            
            ps.setString(4, p.getEstado());            
            ps.executeUpdate();            
            // Obtiene el ID 
            try (var rs = ps.getGeneratedKeys()) {                
                if (rs.next()) {                    
                    idPrestamoGenerado = rs.getInt(1);                
                }            }        }        
        
            // Relaciona cada libro con el préstamo 
            String sqlPrestamoLibro = "INSERT INTO prestamo_libro (id_prestamo, id_libro) VALUES (?, ?)";        
            String sqlActualizarLibro = "UPDATE libros SET disponible = FALSE WHERE isbn = ?";        
            for (Libro l : librosPrestados) {            
                int idLibro = obtenerIdLibroPorIsbn(con, l.getIsbn());            
                try (var ps = con.prepareStatement(sqlPrestamoLibro)) {                
                    ps.setInt(1, idPrestamoGenerado);                
                    ps.setInt(2, idLibro);                
                    ps.executeUpdate();            }            
                try (var ps = con.prepareStatement(sqlActualizarLibro)) {                
                    ps.setString(1, l.getIsbn());                
                    ps.executeUpdate();            }        }    
        } catch (SQLException e) {        
            // Muestra un mensaje si ocurre un error al conectar o guardar en la base de datos
            System.out.println("Error al guardar préstamo: " + e.getMessage());    
        }    
    
        // Devuelve el objeto del préstamo creado
        return p;
    }
    
    //busca prestamos activos
    public Prestamo buscarPrestamoActivoPorLibro(String isbn) {
        for (Prestamo p : prestamos) {
            if (p.getEstado().equalsIgnoreCase("activo")) {
                for (Libro l : p.getLibros()) {
                    if (l.getIsbn().equals(isbn)) {
                        return p;
                    }
                }
            }
        }
        return null;
    }
    //Retorna la lista de prestamos
    public List<Prestamo> getPrestamos() {
        return prestamos;
    }
    //verifica si hay prestamos activos
    public void listarPrestamosActivos() {
        boolean hay = false;
        System.out.println("=== Préstamos Activos ===");
        for (Prestamo p : prestamos) {
            if (p.getEstado().equalsIgnoreCase("activo")) {
                System.out.println(p);
                hay = true;
            }
        }
        if (!hay) {
            System.out.println("No hay préstamos activos.");
        }
    }
    // Se busca el ID del libro según su ISBN en la consulta SQL
    private int obtenerIdLibroPorIsbn(Connection con, String isbn) throws SQLException {    
        String sql = "SELECT id_libro FROM libros WHERE isbn = ?";    
        try (var ps = con.prepareStatement(sql)) {        
         ps.setString(1, isbn);        
            try (var rs = ps.executeQuery()) {            
                if (rs.next()) {                
                    // Si el libro existe, devuelve su ID numérico
                    return rs.getInt("id_libro");            
                }        
         }    
        }    
        //Si no encuentra ningún libro con ese ISBN, devuelve -1 como indicador
        return -1;
    }

    //---solicitudes de compra de libros---
    public void agregarSolicitud(SolicitudCompra solicitud){
        if(solicitud != null){
            solicitudes.add(solicitud);
        }
    }
    //metodo para crear una solicitud de compra de libros
    public SolicitudCompra crearSolicitud(int cantidad, String observaciones) {
        SolicitudCompra s = new SolicitudCompra(contadorSolicitudes++, java.time.LocalDate.now(), cantidad, observaciones);
        solicitudes.add(s);
        //Conecta a la base de datos para guardar la solicitud
        try (Connection con = ConexionBD.obtenerConexion()) {
            String sql = "INSERT INTO solicitudes_compra (fecha_solicitud, cantidad, estado, observaciones) VALUES (?, ?, ?, ?)";
            try (var ps = con.prepareStatement(sql)) {
                ps.setString(1, s.getFechaSolicitud().toString());
                ps.setInt(2, s.getCantidad());
                ps.setString(3, s.getEstado());
                ps.setString(4, s.getObservaciones());
                // Ejecuta la inserción en la base de datos
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            // Muestra un mensaje si ocurre un error
            System.out.println("Error al guardar solicitud: " + e.getMessage());
        }
        return s;
    }
    //retorna la lista de las solicitudes de compra
    public List <SolicitudCompra> getSolicitudes(){
        return solicitudes;
    }
    //busca la solicitud por medio de un id
    public SolicitudCompra buscarSolicitudPorId(int id){
        for(SolicitudCompra s : solicitudes){
            if(s.getIdSolicitud() == id){
                return s;
            }
        }
        return null;
    }

    // Método para cargar todos los datos desde la base de datos
    public void cargarDatosDesdeBD() {
        //Carga los Socios
        try (Connection con = ConexionBD.obtenerConexion()) {
            String sql = "SELECT * FROM socios";
            try (var ps = con.prepareStatement(sql); var rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id_socio");
                    String nombre = rs.getString("nombre");
                    String apellido = rs.getString("apellido");
                    String dni = rs.getString("dni");
                    String domicilio = rs.getString("domicilio");
                    String telefono = rs.getString("telefono");
                    String email = rs.getString("email");
                    String fechaRegistro = rs.getString("fecha_registro");
                    boolean activo = rs.getBoolean("activo");

                    Socio socio = new Socio(id, nombre, apellido, dni, domicilio, telefono, email, fechaRegistro);
                    if (!activo) {
                        socio.darDeBaja(); // Si en la BD esta inactivo, tambien lo marca en memoria
                    }
                    socios.add(socio);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar socios desde la BD: " + e.getMessage());//Muestra mensaje de error
        }
        //Carga los libros
        try (Connection con = ConexionBD.obtenerConexion()) {
            String sql = "SELECT * FROM libros";
            try (var ps = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS); var rs = ps.executeQuery()) {
                while (rs.next()) {
                    String titulo = rs.getString("titulo");
                    String autor = rs.getString("autor");
                    String editorial = rs.getString("editorial");
                    int año = rs.getInt("año");
                    String isbn = rs.getString("isbn");
                    String categoria = rs.getString("categoria");
                    boolean disponible = rs.getBoolean("disponible");
                    LibroNacional libro = new LibroNacional(isbn, titulo, autor, editorial, año, categoria, 0.0);
                    libro.setDisponible(disponible);
                    libros.add(libro);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar libros desde la BD: " + e.getMessage());//muestra mensaje de error
        }
    }
    

    // ---Informacion general---
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public boolean haySocios() { return !socios.isEmpty(); }
    public boolean hayLibros() { return !libros.isEmpty(); }
    public boolean hayPrestamos() { return !prestamos.isEmpty(); }

    @Override //Devuelve lista ordenada
    public String toString() {
        return "Biblioteca: " + nombre +
               " | Socios: " + socios.size() +
               " | Libros: " + libros.size() +
               " | Préstamos: " + prestamos.size();
    }
}
