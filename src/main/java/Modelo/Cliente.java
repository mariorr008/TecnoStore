package Modelo;

public class Cliente extends Persona{

    public Cliente(int id, String nombre, String cedula, String correo, String telefono, String password) {
        super(id, nombre, cedula, correo, telefono, password);
    }

    @Override
    public void mostrarMenu() {
        
    }
    
}
