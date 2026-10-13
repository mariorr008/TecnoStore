package Vista;

public class MenuCelulares {

    private final Validaciones v;

    public MenuCelulares(Validaciones v) {
        this.v = v;
    }

    // ============ CELULARES ============
    public int mostrarMenuCelulares() {
        System.out.println("""
            ===== GESTIÓN CELULARES =====
            1. Registrar celular
            2. Listar celulares
            3. Buscar celular por ID
            4. Modificar celular
            5. Eliminar celular
            6. Filtrar por gama
            7. Gestionar marcas
            0. Volver
            """);
        return v.pedirEntero("Opción: ");
    }

    // ============ MARCAS ============
    public int mostrarMenuMarcas() {
        System.out.println("""
            ===== GESTIÓN MARCAS =====
            1. Registrar marca
            2. Listar marcas
            0. Volver
            """);
        return v.pedirEntero("Opción: ");
    }
}