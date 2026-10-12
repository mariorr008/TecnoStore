package Vista;

import Modelo.Cliente;

public class IngresoDatos {

    private final Validaciones v;

    public IngresoDatos(Validaciones v) {
        this.v = v;
    }

    // ============ LOGIN ============
    public String pedirCorreoLogin() {
        return v.pedirCorreo("Correo: ");
    }

    public String pedirPasswordLogin() {
        return v.pedirTexto("Contraseña: ");
    }

    // ============ CLIENTE ============
    public Cliente pedirCliente() {
        String nombre = v.pedirSoloLetras("Nombre: ");
        String identificacion = v.pedirIdentificacion("Identificación (6-10 dígitos): ");
        String correo = v.pedirCorreo("Correo: ");
        String telefono = v.pedirTelefono("Teléfono (7-10 dígitos): ");
        String password = v.pedirPassword("Contraseña (mín 4 caracteres): ");

        return new Cliente(0, nombre, identificacion, correo, telefono, password);
    }
}