package Vista;

public class IngresoVenta {

    private final Validaciones v;

    public IngresoVenta(Validaciones v) {
        this.v = v;
    }

    public int pedirIdCelular() {
        return v.pedirEnteroPositivo("ID del celular: ");
    }

    public int pedirCantidad() {
        return v.pedirEnteroPositivo("Cantidad: ");
    }

    public boolean pedirAgregarOtro() {
        return v.pedirSiNo("¿Añadir otro producto?");
    }

    public boolean confirmarCompra() {
        return v.pedirSiNo("¿Confirmar compra?");
    }
}