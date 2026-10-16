package gestionBiblioteca;
/*Clase encargada de la gestion de 
Exepciones, siendo una extencion de Execption 
*/
public class BibliotecaExepciones extends Exception {
    //Mensaje de Exepciones
    public BibliotecaExepciones(String mensaje){
        super(mensaje);
    }
}
