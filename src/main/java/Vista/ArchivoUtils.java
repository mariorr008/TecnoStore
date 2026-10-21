package Vista;

import Dao.VentaDao;
import Modelo.ItemVenta;
import Modelo.ReporteVenta;
import Modelo.Venta;
import java.util.ArrayList;
import java.util.List;

public class ArchivoUtils {

    public static void generarReporteVentas(
            List<Venta> ventas, VentaDao ventaDAO) {

        List<ReporteVenta> reportes = new ArrayList<>();

        for (Venta venta : ventas) {
            List<ItemVenta> items =
                    ventaDAO.listarItemsDeVenta(venta.getId());

            List<String> productos = new ArrayList<>();

            for (ItemVenta item : items) {
                productos.add(item.toString());
            }

            ReporteVenta reporte = new ReporteVenta(
                venta.getId(),
                venta.getFecha().toString(),
                venta.getCliente().getNombre(),
                productos,
                venta.getTotal()
            );

            reportes.add(reporte);
        }

        DatosVenta datosVenta = new DatosVenta();
        datosVenta.guardarVentas(reportes);

        System.out.println("Archivo ventas.dat generado");
    }
    
    public static void leerReporteVentas() {
    DatosVenta datos = new DatosVenta();
    List<ReporteVenta> ventas = datos.obtenerVentas();

    if (ventas.isEmpty()) {
        System.out.println("No hay ventas guardadas en el archivo.");
        return;
    }

    for (ReporteVenta venta : ventas) {
        System.out.println("\n========== VENTA #" + venta.getId() + " ==========");
        System.out.println("Fecha: " + venta.getFecha());
        System.out.println("Cliente: " + venta.getCliente());
        System.out.println("Productos:");

        for (String producto : venta.getProductos()) {
            System.out.println("  - " + producto);
        }

        System.out.println("Total: $" + venta.getTotal());
        System.out.println("==================================");
    }
}
}
