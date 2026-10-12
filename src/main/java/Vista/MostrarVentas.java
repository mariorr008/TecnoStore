package vista;

import java.util.List;
import Modelo.ItemVenta;
import Modelo.Venta;

public class MostrarVentas {

    public void mostrarVentas(List<Venta> ventas) {
        System.out.println("=== VENTAS ===");
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas");
            return;
        }
        for (Venta v : ventas) {
            System.out.println(v);
        }
    }

    public void mostrarDetalleVenta(Venta venta, List<ItemVenta> items) {
        System.out.println("""
                === DETALLE DE VENTA #%d ===
                Cliente: %s
                Fecha: %s
                Productos:
                %s
                Total: $%s
                """.formatted(
                    venta.getId(),
                    venta.getCliente().getNombre(),
                    venta.getFecha(),
                    items.stream()
                            .map(item -> "  - " + item)
                            .collect(java.util.stream.Collectors.joining("\n")),
                    venta.getTotal()
                ));
    }
}