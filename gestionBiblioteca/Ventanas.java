package gestionBiblioteca;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
 Clase Ventanas: Maneja la interfaz gráfica de usuario mediante cuadros de diálogo 
 (JOptionPane) para interactuar y gestionar las operaciones del sistema de la biblioteca.
 */

public class Ventanas {

    // --- Agregar Socio ---
    public static void agregarSocio(Biblioteca biblioteca) {
        /* Bloque try para capturar cualquier error inesperado durante la carga de datos
        JOptionPane.showInputDialog abre una ventana pidiendo datos al usuario
        */
        try {
            String nombre = JOptionPane.showInputDialog("Nombre del socio:");
            if (nombre == null) return;
            String apellido = JOptionPane.showInputDialog("Apellido:");
            if (apellido == null) return;
            String dni = JOptionPane.showInputDialog("DNI:");
            if (dni == null) return;
            String domicilio = JOptionPane.showInputDialog("Domicilio:");
            if (domicilio == null) return;
            String telefono = JOptionPane.showInputDialog("Teléfono:");
            if (telefono == null) return;
            String email = JOptionPane.showInputDialog("Email:");
            if (email == null) return;

            if (biblioteca.buscarSocioPorDni(dni) != null) {
                JOptionPane.showMessageDialog(null, "Ya existe un socio con ese DNI.");
                return;
            }
            Socio socio = new Socio(0, nombre, apellido, dni, domicilio, telefono, email, LocalDate.now().toString());
            biblioteca.agregarSocio(socio);
            JOptionPane.showMessageDialog(null, "Socio agregado correctamente.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    // --- Modificar Socio ---
    public static void modificarSocio(Biblioteca biblioteca) {
        // Bloque para controlar errores al buscar o actualizar los datos del socio
        try {
            String dni = JOptionPane.showInputDialog("DNI del socio a modificar:");
            if (dni == null) return;

            Socio socio = biblioteca.buscarSocioPorDni(dni);
            if (socio == null) {
                JOptionPane.showMessageDialog(null, "Socio no encontrado.");
                return;
            }
            //Abre una ventana pidiendo los datos
            String nombre = JOptionPane.showInputDialog("Nuevo nombre:", socio.getNombre());
            if (nombre == null) return;
            String apellido = JOptionPane.showInputDialog("Nuevo apellido:", socio.getApellido());
            if (apellido == null) return;
            String domicilio = JOptionPane.showInputDialog("Nuevo domicilio:", socio.getDomicilio());
            if (domicilio == null) return;
            String telefono = JOptionPane.showInputDialog("Nuevo teléfono:", socio.getTelefono());
            if (telefono == null) return;
            String email = JOptionPane.showInputDialog("Nuevo email:", socio.getEmail());
            if (email == null) return;
            //Se actualizan la infromacion del socio
            socio.setNombre(nombre);
            socio.setApellido(apellido);
            socio.setDomicilio(domicilio);
            socio.setTelefono(telefono);
            socio.setEmail(email);
            //Mensaje de modificacion exitosa
            JOptionPane.showMessageDialog(null, "Socio modificado correctamente.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());//mensaje de error
        }
    }

    // --- Dar de Baja Socio ---
    public static void darDeBajaSocio(Biblioteca biblioteca) {
        //Controla posibles errores al dar de baja y busca el socio por dni
        try {
            String dni = JOptionPane.showInputDialog("DNI del socio a dar de baja:");
            if (dni == null) return;

            Socio socio = biblioteca.buscarSocioPorDni(dni);
            if (socio == null) {
                JOptionPane.showMessageDialog(null, "Socio no encontrado.");
                return;
            }
            //Pide la confirmacion para la eliminacion
            int confirmacion = JOptionPane.showConfirmDialog(null,
                "¿Confirma dar de baja al socio " + socio.getApellido() + ", " + socio.getNombre() + "?",
                "Confirmar baja", JOptionPane.YES_NO_OPTION);
            //si se confirma se procesa la baja
            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean ok = biblioteca.darDeBajaSocio(dni);
                if (ok) {
                    JOptionPane.showMessageDialog(null, "Socio dado de baja correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "No se puede dar de baja: el socio tiene préstamos activos.");
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());//muestra mensaje de error
        }
    }

    // --- Agregar Libro ---
    public static void agregarLibro(Biblioteca biblioteca) {
        //Controla errores al ingresar datos o convertir números de los libros
        try {
            String tipo = JOptionPane.showInputDialog("Tipo de libro:\n1 = Nacional\n2 = Importado");
            if (tipo == null) return;
            String isbn = JOptionPane.showInputDialog("ISBN:");
            if (isbn == null) return;
            String titulo = JOptionPane.showInputDialog("Título:");
            if (titulo == null) return;
            String autor = JOptionPane.showInputDialog("Autor:");
            if (autor == null) return;
            String editorial = JOptionPane.showInputDialog("Editorial:");
            if (editorial == null) return;
            int año = Integer.parseInt(JOptionPane.showInputDialog("Año:"));
            String categoria = JOptionPane.showInputDialog("Categoría:");
            if (categoria == null) return;

            Libro libro;
            if (tipo.equals("1")) {//Si el usuario escribe 1 crea un libro nacional y pide el precio en pesos
                double precio = Double.parseDouble(JOptionPane.showInputDialog("Precio en pesos:"));
                libro = new LibroNacional(isbn, titulo, autor, editorial, año, categoria, precio);
            } else {//Sino se pide el precio en dolares y el pais
                double precioDolar = Double.parseDouble(JOptionPane.showInputDialog("Precio en dólares:"));
                String pais = JOptionPane.showInputDialog("País de origen:");
                libro = new LibroImportado(isbn, titulo, autor, editorial, año, categoria, precioDolar, pais);
            }
            biblioteca.agregarLibro(libro);//Guarada el libro y se muestra un mensaje
            JOptionPane.showMessageDialog(null, "Libro agregado correctamente.");

        } catch (NumberFormatException e) {//Si el ususario escribe letras y no numeros se muetra mensaje de error
            JOptionPane.showMessageDialog(null, "Error: debe ingresar un número válido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    // --- Listar Socios ---
    public static void listarSocios(Biblioteca biblioteca) {
        //Si no hay socios muestra mensaje y cierra
        if (biblioteca.getSocios().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay socios registrados.");
            return;
        }
        StringBuilder sb = new StringBuilder("=== Lista de Socios ===\n\n");
        for (Socio s : biblioteca.getSocios()) {
            sb.append(s).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());//Muestra todos los socios
    }

    // --- Listar Libros ---
    public static void listarLibros(Biblioteca biblioteca) {
        //Si no hay libros muestra mensaje y cierra
        if (biblioteca.getLibros().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay libros registrados.");
            return;
        }
        StringBuilder sb = new StringBuilder("=== Catálogo de Libros ===\n\n");
        for (Libro l : biblioteca.getLibros()) {
            sb.append(l).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());//Muestra la lista de todos los libros
    }

    // --- Realizar Préstamo ---
    public static void realizarPrestamo(Biblioteca biblioteca) {
        //Controla errores al buscar socio/libro y registrar el préstamo
        try {
            String dni = JOptionPane.showInputDialog("DNI del socio:");
            if (dni == null) return;
            Socio socio = biblioteca.buscarSocioPorDni(dni);
            if (socio == null) {
                JOptionPane.showMessageDialog(null, "Socio no encontrado.");
                return;
            }
            if (!socio.isActivo() || socio.tieneMultasPendientes()) {//Si el socio tiene multas pendientes no puede pedir otro prestamo
                JOptionPane.showMessageDialog(null, "El socio no puede recibir préstamos (inactivo o con multas).");
                return;
            }
            //Se ingresa el ISNB del libro y se busca en la lista y verifica que exista 
            String isbn = JOptionPane.showInputDialog("ISBN del libro:");
            if (isbn == null) return;
            Libro libro = biblioteca.buscarLibroPorIsbn(isbn);
            if (libro == null || !libro.isDisponible()) {
                JOptionPane.showMessageDialog(null, "Libro no disponible.");
                return;
            }
            //Se agraga el libro a la lista y se registra el prestamo
            List<Libro> lista = new ArrayList<>();
            lista.add(libro);
            Prestamo p = biblioteca.registrarPrestamo(socio, lista);
            if (p != null) {
                JOptionPane.showMessageDialog(null, "Préstamo registrado:\n\n" + p);
            }

        } catch (Exception e) {//muestra mensaje de error
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    // --- Procesar Devolución ---
    public static void procesarDevolucion(Biblioteca biblioteca) {
        //Controla errores al buscar el préstamo activo y registrar la devolución
        try {
            String isbn = JOptionPane.showInputDialog("ISBN del libro a devolver:");
            if (isbn == null) return;
            Prestamo p = biblioteca.buscarPrestamoActivoPorLibro(isbn);
            if (p == null) {
                JOptionPane.showMessageDialog(null, "No se encontró préstamo activo para ese libro.");
                return;
            }
            p.finalizar(LocalDate.now());
            JOptionPane.showMessageDialog(null, "Devolución registrada:\n\n" + p);

        } catch (Exception e) {//Mensaje de error
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    // Crea una lista de prestamos Activos
    public static void listarPrestamosActivos(Biblioteca biblioteca) {
        StringBuilder sb = new StringBuilder("=== Préstamos Activos ===\n\n");
        boolean hay = false;
        for (Prestamo p : biblioteca.getPrestamos()) {
            if (p.getEstado().equalsIgnoreCase("activo")) {
                sb.append(p).append("\n");
                hay = true;
            }
        }
        if (!hay) {//Muestra mensaje de que no hay prestamo
            JOptionPane.showMessageDialog(null, "No hay préstamos activos.");
        } else {
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    }

    // --- Solicitar Compra ---
    public static void solicitarCompra(Biblioteca biblioteca) {
        //Controla y capturar errores de formato numérico al ingresar la cantidad de ejemplares
        try {
            String cantidadStr = JOptionPane.showInputDialog("Cantidad de ejemplares a solicitar:");
            if (cantidadStr == null) return;
            int cantidad = Integer.parseInt(cantidadStr);

            if (cantidad <= 0) {//Muestra menaje de error
                JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a 0.");
                return;
            }

            String observaciones = JOptionPane.showInputDialog("Observaciones (opcional):");
            if (observaciones == null) observaciones = "";

            SolicitudCompra s = biblioteca.crearSolicitud(cantidad, observaciones);
            JOptionPane.showMessageDialog(null, "Solicitud registrada:\n\n" + s);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: la cantidad debe ser un número.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    // --- Listar Solicitudes ---
    public static void listarSolicitudes(Biblioteca biblioteca) {
        //Si no hay solicitudes, muestra mensaje y termina
        if (biblioteca.getSolicitudes().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay solicitudes registradas.");
            return;
        }
        StringBuilder sb = new StringBuilder("=== Solicitudes de Compra ===\n\n");
        for (SolicitudCompra s : biblioteca.getSolicitudes()) {
            sb.append(s).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());//Muestra todas las solicitudes
    }
}