package Modelo;

public abstract class Persona {
    protected int id;
    protected String nombre;
    protected String identificacion;
    protected String correo;
    protected String telefono;
    protected String password;

    public Persona(int id, String nombre, String identificacion, String correo, String telefono, String password) {
        this.id = id;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.correo = correo;
        this.telefono = telefono;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public abstract void mostrarMenu();

    @Override
    public String toString() {
        return """
               ID:          %s
               Nombre:      %s
               Correo:      %s
               Telefono:    %s
               """.formatted(id,nombre,correo,telefono);
    }
}
