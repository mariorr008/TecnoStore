package Controlador;

import Dao.CelularDaoSQL;
import Dao.VentaDaoSQL;
import java.util.ArrayList;
import java.util.List;
import Modelo.Celular;
import Modelo.Cliente;
import Modelo.ItemVenta;
import Modelo.Venta;
import Modelo.StockInsuficienteException;
import Vista.*;

public class ControladorVentas {

    private final VentaDaoSQL ventaDAO = new VentaDaoSQL();
    private final CelularDaoSQL celularDAO = new CelularDaoSQL();
    private final GestorVentas gestor = new GestorVentas(ventaDAO, celularDAO);
    private final GestorCelulares gestorCelulares =
        new GestorCelulares(celularDAO, new Dao.MarcaDaoSQL());

    private final MenuVentas menu;
    private final MenuCliente menuCliente;
    private final IngresoVenta ingresoVenta;
    private final IngresoDatos ingresoDatos;
    private final MostrarVentas mostrar;
    private final MostrarDatos mostrarDatos;

    public ControladorVentas(Validaciones v) {
        this.menu = new MenuVentas(v);
        this.menuCliente = new MenuCliente(v);
        this.ingresoVenta = new IngresoVenta(v);
        this.ingresoDatos = new IngresoDatos(v);
        this.mostrar = new MostrarVentas();
        this.mostrarDatos = new MostrarDatos();
    }

    // ============ VENTAS (ADMIN) ============
    public void iniciarVentas() {
        int op;
        do {
            op = menu.mostrarMenuVentas();
            switch (op) {
                case 1 -> mostrar.mostrarVentas(gestor.listarTodas());
                case 2 -> verDetalleVenta();
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción inválida");
            }
        } while (op != 0);
    }

    private void verDetalleVenta() {
        try {
            int id = ingresoDatos.pedirIdVenta();
            Venta venta = ventaDAO.bucarPorId(id);
            if (venta == null) {
                System.out.println("No existe la venta");
                return;
            }
            List<ItemVenta> items = ventaDAO.listarItemsDeVenta(id);
            mostrar.mostrarDetalleVenta(venta, items);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ============ REPORTES (ADMIN) ============
    public void iniciarReportes() {
        int op;
        do {
            op = menu.mostrarMenuReportes();
            StrategyReporte estrategia = switch (op) {
                case 1 -> new ReporteStockBajo(celularDAO);
                case 2 -> new ReporteTop3Vendidos(ventaDAO);
                case 3 -> new ReporteVentasPorMes(ventaDAO);
                case 0 -> null;
                default -> null;
            };
            if (estrategia != null) {
                estrategia.generar();
            }
        } while (op != 0);
    }

    // ============ ARCHIVO (ADMIN) ============
    public void generarArchivo() {
        try {
            ArchivoUtils.generarReporteVentas(ventaDAO.listarTodas(), ventaDAO);
            System.out.println("Archivo reporte_ventas.txt generado");
        } catch (Exception e) {
            System.out.println("Error al generar archivo: " + e.getMessage());
        }
    }

    // ============ COMPRA (CLIENTE) ============
    public void realizarCompra(Cliente cliente) {
        try {
            mostrarDatos.mostrarCelulares(gestorCelulares.listarTodos());

            List<ItemVenta> items = new ArrayList<>();
            boolean seguir;
            do {
                int idCelular = ingresoVenta.pedirIdCelular();
                Celular c = gestorCelulares.buscarPorId(idCelular);
                int cantidad = ingresoVenta.pedirCantidad();
                items.add(new ItemVenta(0, c, cantidad));
                seguir = ingresoVenta.pedirAgregarOtro();
            } while (seguir);

            if (items.isEmpty()) {
                System.out.println("No hay productos en la compra");
                return;
            }

            if (!ingresoVenta.confirmarCompra()) {
                System.out.println("Compra cancelada");
                return;
            }

            Venta venta = gestor.registrarVenta(cliente, items);
            System.out.println("Compra realizada. Total: $" + venta.getTotal());
        } catch (StockInsuficienteException e) {
            System.out.println("Stock insuficiente: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ============ HISTORIAL (CLIENTE) ============
    public void verHistorial(Cliente cliente) {
        List<Venta> ventas = gestor.listarPorCliente(cliente.getId());
        mostrar.mostrarVentas(ventas);

        if (ventas.isEmpty()) return;

        int op = menuCliente.mostrarMenuHistorial();
        if (op == 1) {
            int idVenta = ingresoDatos.pedirIdVenta();
            Venta v = ventaDAO.bucarPorId(idVenta);
            if (v != null) {
                List<ItemVenta> items = ventaDAO.listarItemsDeVenta(idVenta);
                mostrar.mostrarDetalleVenta(v, items);
            }
        }
    }
}