package Vista;

public class MenuCliente {

    private final Validaciones v;

    public MenuCliente(Validaciones v) {
        this.v = v;
    }

    // ============ MENÚ PRINCIPAL CLIENTE ============
    public int mostrarMenuCliente() {
        System.out.println("""
            ========= MENÚ CLIENTE =========
            1. Ver catálogo de celulares
            2. Realizar compra
            3. Ver mi historial de compras
            0. Cerrar sesión
            """);
        return v.pedirEntero("Opción: ");
    }

    // ============ HISTORIAL ============
    public int mostrarMenuHistorial() {
        System.out.println("""
            ===== HISTORIAL =====
            1. Ver detalle de una venta
            0. Volver
            """);
        return v.pedirEntero("Opción: ");
    }

}