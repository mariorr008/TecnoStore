package Vista;

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
            System.out.printf("Venta #%d | %s | Cliente: %s | Total: $%.2f%n",
                v.getId(),
                v.getFecha(),
                v.getCliente().getNombre(),
                v.getTotal());
        }
    }

    public void mostrarDetalleVenta(Venta venta, List<ItemVenta> items) {
        System.out.println("""
                === DETALLE DE VENTA #%d ===
                Cliente: %s
                Fecha: %s
                Productos:
                """.formatted(
                    venta.getId(),
                    venta.getCliente().getNombre(),
                    venta.getFecha()));

        for (ItemVenta item : items) {
            System.out.printf("  - %s %s x%d = $%.2f%n",
                item.getCelular().getMarca().getNombreMarca(),
                item.getCelular().getModelo(),
                item.getCantidad(),
                item.getSubtotal());
        }

        System.out.printf("Total: $%.2f%n", venta.getTotal());
    }
}