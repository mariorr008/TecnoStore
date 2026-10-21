package Vista;

public class MenuAdmin {

    private final Validaciones v;

    public MenuAdmin(Validaciones v) {
        this.v = v;
    }

    // ============ MENÚ PRINCIPAL ADMIN ============
    public int mostrarMenuAdmin() {
        System.out.println("""
            ========= MENÚ ADMIN =========
            1. Gestionar celulares
            2. Gestionar clientes
            3. Ver todas las ventas
            4. Ver reportes
            5. Generar archivo de reportes
            6. Leer archivo de reportes
            0. Cerrar sesión
            """);
        return v.pedirEntero("Opción: ");
    }

    // ============ GESTIÓN CLIENTES ============
    public int mostrarMenuClientes() {
        System.out.println("""
            ===== GESTIÓN CLIENTES =====
            1. Listar clientes
            2. Buscar cliente por ID
            0. Volver
            """);
        return v.pedirEntero("Opción: ");
    }
}