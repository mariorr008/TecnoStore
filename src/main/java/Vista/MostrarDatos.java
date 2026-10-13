package Vista;

import java.util.List;
import Modelo.Celular;
import Modelo.Cliente;
import Modelo.Marca;

public class MostrarDatos {

    public void mostrarCelulares(List<Celular> celulares) {
        System.out.println("=== CATÁLOGO DE CELULARES ===");
        if (celulares.isEmpty()) {
            System.out.println("No hay celulares registrados");
            return;
        }
        System.out.printf("%-7s | %-10s | %-15s | %-8s | %-6s | %-12s | %-6s%n",
            "ID", "Marca", "Modelo", "SO", "Gama", "Precio", "Stock");
        for (Celular c : celulares) {
            System.out.printf("%-7d | %-10s | %-15s | %-8s | %-6s | %-12.2f | %-6d%n",
                c.getId(),
                c.getMarca().getNombreMarca(),
                c.getModelo(),
                c.getSistemaOp(),
                c.getGama(),
                c.getPrecio(),
                c.getStock());
        }
    }

    public void mostrarClientes(List<Cliente> clientes) {
        System.out.println("=== CLIENTES ===");
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados");
            return;
        }
        for (Cliente c : clientes) {
            System.out.println(c.getId() + " | " + c.getNombre()
                + " | " + c.getIdentificacion() + " | " + c.getCorreo());
        }
    }

    public void mostrarMarcas(List<Marca> marcas) {
        System.out.println("=== MARCAS ===");
        if (marcas.isEmpty()) {
            System.out.println("No hay marcas registradas");
            return;
        }
        for (Marca m : marcas) {
            System.out.println(m.getId() + " | " + m.getNombreMarca());
        }
    }
}