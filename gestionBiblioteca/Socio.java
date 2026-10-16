package gestionBiblioteca;

/* Clase encargada de generar, almacenar y modificar los datos de un socio 
  ver si este cuenta con multas o no*/

public class Socio {
  //Atributos de la clase
    private int idSocio;
    private String nombre;
    private String apellido;
    private String dni;
    private String domicilio;
    private String telefono;
    private String email;
    private String fechaRegistro;
    private boolean activo;
    private double multasPendientes;

  //Metodo constructos propio para la Clase socio 
    public Socio(int idSocio, String nombre, String apellido, String dni, String domicilio, String telefono, String email, String fechaResgistro){
        this.idSocio = idSocio;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this. domicilio = domicilio;
        this.telefono = telefono;
        this.email = email;
        this.fechaRegistro = fechaResgistro;
        this.activo = true;
        this.multasPendientes = 0.0;
      }
      //Metodo que acumula las multas de los socios
      public void acumularMultas(double monto){
        this.multasPendientes += monto;
      }
      //Metodo para saber si el socio tiene multas
      public boolean tieneMultasPendientes(){
        return multasPendientes > 0;
      }
      //Metodo para dar de baja un socio
      public void darDeBaja(){
        this.activo = false;
      }

        //Metodos gets y sets de los atributos de los Socios 
        public int getIdSocio() { return idSocio; }
        public void setIdSocio(int idSocio) { this.idSocio = idSocio; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getApellido() { return apellido; }
        public void setApellido(String apellido) { this.apellido = apellido; }

        public String getDni() { return dni; }
        public void setDni(String dni) { this.dni = dni; }

        public String getDomicilio() { return domicilio; }
        public void setDomicilio(String domicilio) { this.domicilio = domicilio; }

        public String getTelefono() { return telefono; }
        public void setTelefono(String telefono) { this.telefono = telefono; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getFechaRegistro() { return fechaRegistro; }
        public void setFechaRegistro(String fechaRegistro) { this.fechaRegistro = fechaRegistro; }

        public boolean isActivo() { return activo; }
        public void setActivo(boolean activo) { this.activo = activo; }

        public double getMultasPendientes() { return multasPendientes; }

        //Metodo Override que devuelve mensaje ordenado
        @Override
        public String toString() {
            return "ID: " + idSocio + " | " + apellido + ", " + nombre +
               " | DNI: " + dni + " | Tel: " + telefono +
               " | Email: " + email +
               " | Estado: " + (activo ? "Activo" : "Inactivo") +
               " | Multas: $" + multasPendientes;
    }
}
