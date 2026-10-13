package Vista;

public class MenuVentas {

    private final Validaciones v;

    public MenuVentas(Validaciones v) {
        this.v = v;
    }

    // ============ VENTAS ============
    public int mostrarMenuVentas() {
        System.out.println("""
            ===== VENTAS =====
            1. Ver todas las ventas
            2. Ver detalle de una venta
            0. Volver
            """);
        return v.pedirEntero("Opción: ");
    }

    // ============ REPORTES ============
    public int mostrarMenuReportes() {
        System.out.println("""
            ===== REPORTES =====
            1. Celulares con stock bajo
            2. Top 3 celulares más vendidos
            3. Ventas totales por mes
            0. Volver
            """);
        return v.pedirEntero("Opción: ");
    }
}