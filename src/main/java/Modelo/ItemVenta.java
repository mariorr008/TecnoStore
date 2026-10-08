package Modelo;

public class ItemVenta {
    private final int id;
    private final Celular celular;
    private final int cantidad;
    private final double precioUnitario;
    private final double subtotal;

    public ItemVenta(int id, Celular celular, int cantidad) {
        this.id = id;
        this.celular = celular;
        this.cantidad = cantidad;
        this.precioUnitario = celular.getPrecio();
        this.subtotal = precioUnitario * cantidad;
    }

    public int getId() {
        return id;
    }

    public Celular getCelular() {
        return celular;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    @Override
    public String toString() {
        return """
               ====ItemVenta====
               Marca:       %s
               Modelo:      %s
               Cantidad:    %s
               Subtotal:    %s
               """.formatted(celular.getMarca(),celular.getModelo(),cantidad,subtotal);
    }
}
