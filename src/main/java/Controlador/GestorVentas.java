package Controlador;

import Dao.CelularDao;
import Dao.VentaDao;
import java.util.List;
import Modelo.Celular;
import Modelo.Cliente;
import Modelo.GenerarId;
import Modelo.ItemVenta;
import Modelo.Venta;
import Modelo.StockInsuficienteException;

public class GestorVentas {

    private static final double IVA = 0.19;

    private final VentaDao ventaDAO;
    private final CelularDao celularDAO;

    public GestorVentas(VentaDao ventaDAO, CelularDao celularDAO) {
        this.ventaDAO = ventaDAO;
        this.celularDAO = celularDAO;
    }

    public Venta registrarVenta(Cliente cliente, List<ItemVenta> items) {
        // Validar stock de cada item
        for (ItemVenta item : items) {
            Celular c = item.getCelular();
            if (c.getStock() < item.getCantidad()) {
                throw new StockInsuficienteException(
                    "Stock insuficiente para " + c.getMarca() + " " + c.getModelo()
                );
            }
        }

        // Crear venta
        int idVenta;
        do {
            idVenta = GenerarId.generar();
        } while (ventaDAO.existe(idVenta));

        Venta venta = new Venta(idVenta, cliente);

        for (ItemVenta item : items) {
            int idItem;
            do {
                idItem = GenerarId.generar();
            } while (false);

            ItemVenta nuevoItem = new ItemVenta(
                idItem,
                item.getCelular(),
                item.getCantidad()
            );
            venta.agregarItem(nuevoItem);
        }

        // Calcular total con IVA
        double subtotal = venta.getItems().stream()
            .mapToDouble(ItemVenta::getSubtotal)
            .sum();
        double iva = subtotal * IVA;
        double total = subtotal + iva;

        venta.setTotal(total); 

        // Guardar venta y detalle
        ventaDAO.guardar(venta);

        // Actualizar stock
        for (ItemVenta item : venta.getItems()) {
            Celular c = item.getCelular();
            int nuevoStock = c.getStock() - item.getCantidad();
            celularDAO.actualizarStock(c.getId(), nuevoStock);
        }

        return venta;
    }

    public List<Venta> listarTodas() {
        return ventaDAO.listarTodas();
    }

    public List<Venta> listarPorCliente(int idCliente) {
        return ventaDAO.listarPorCliente(idCliente);
    }

    public List<ItemVenta> listarItems(int idVenta) {
        return ventaDAO.listarItemsDeVenta(idVenta);
    }
}