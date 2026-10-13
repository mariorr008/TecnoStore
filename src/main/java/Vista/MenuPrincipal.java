package Vista;

public class MenuPrincipal {

    private final Validaciones v;

    public MenuPrincipal(Validaciones v) {
        this.v = v;
    }

    // ============ MENÚ PRINCIPAL ============
    public int mostrarMenuPrincipal() {
        System.out.println("""
            ========= TECNOSTORE =========
            1. Iniciar sesión
            2. Registrarse como cliente
            3. Salir
            """);
        return v.pedirEntero("Opción: ");
    }

    // ============ REGISTRO DE CLIENTE ============
    public int mostrarMenuRegistro() {
        System.out.println("""
            ===== REGISTRO DE CLIENTE =====
            1. Continuar
            0. Cancelar
            """);
        return v.pedirEntero("Opción: ");
    }
}