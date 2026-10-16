package gestionBiblioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/*
  Clase encargada de gestionar la conexión con la base de datos MySQL 
  utilizando JDBC para el sistema de la biblioteca.
 */

public class ConexionBD {
    //configuracion para la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = ""; 
    //Metodo propio que establece un aconecion activa a la base de datos
    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
    //Metodo para cerra la conexion de forma segura con la BD
    public static void cerrar(Connection conexion) {
        if (conexion != null) {//verifica que exista la conexion
            try {
                conexion.close();
            } catch (SQLException e) {
                //se muestra cualquier error inesperdado
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
