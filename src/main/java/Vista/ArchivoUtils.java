package Vista;

import Dao.VentaDao;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import Modelo.ItemVenta;
import Modelo.Venta;

public class ArchivoUtils {

    private static final String ARCHIVO = "reporte_ventas.txt";

    public static void generarReporteVentas(List<Venta> ventas, VentaDao ventaDAO) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO))) {

            writer.write("=========================================");
            writer.newLine();
            writer.write("       REPORTE DE VENTAS - TECNOSTORE");
            writer.newLine();
            writer.write("=========================================");
            writer.newLine();
            writer.newLine();

            if (ventas.isEmpty()) {
                writer.write("No hay ventas registradas.");
                writer.newLine();
                return;
            }

            double totalGeneral = 0;

            for (Venta venta : ventas) {
                writer.write("Venta #" + venta.getId());
                writer.newLine();
                writer.write("Fecha: " + venta.getFecha());
                writer.newLine();
                writer.write("Cliente: " + venta.getCliente().getNombre());
                writer.newLine();
                writer.write("Productos:");
                writer.newLine();

                List<ItemVenta> items = ventaDAO.listarItemsDeVenta(venta.getId());
                for (ItemVenta item : items) {
                    writer.write("  - " + item);
                    writer.newLine();
                }

                writer.write("Total: $" + venta.getTotal());
                writer.newLine();
                writer.write("-----------------------------------------");
                writer.newLine();

                totalGeneral += venta.getTotal();
            }

            writer.newLine();
            writer.write("TOTAL GENERAL: $" + totalGeneral);
            writer.newLine();

            System.out.println("Archivo generado: " + ARCHIVO);

        } catch (IOException e) {
            throw new RuntimeException("Error al generar el archivo", e);
        }
    }
}