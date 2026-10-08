package Modelo;

public class Administrador extends Persona{

    public Administrador(int id, String nombre, String cedula, String correo, String telefono, String password) {
        super(id, nombre, cedula, correo, telefono, password);
    }

    @Override
    public void mostrarMenu() {
        
    }
    
}
