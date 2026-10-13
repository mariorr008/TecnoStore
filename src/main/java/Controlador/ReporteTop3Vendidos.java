package Controlador;

import Dao.VentaDao;
import Modelo.ItemVenta;
import Modelo.Venta;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReporteTop3Vendidos implements StrategyReporte{

    private final VentaDao ventaDAO;

    public ReporteTop3Vendidos(VentaDao ventaDAO) {
        this.ventaDAO = ventaDAO;
    }
    
    @Override
    public void generar() {
        List<Venta> ventas = ventaDAO.listarTodas();

        Map<String, Integer> conteo = new HashMap<>();
        for (Venta v : ventas) {
            List<ItemVenta> items = ventaDAO.listarItemsDeVenta(v.getId());
            for (ItemVenta item : items) {
                String clave = item.getCelular().getMarca().getNombreMarca()
                             + " " + item.getCelular().getModelo();
                //obtiene Marca y modelo del celular para la clave de mapa
                conteo.merge(clave, item.getCantidad(), Integer::sum);
                //merge: Si la clave ya existe suma la cantidad que ya hay
                //sino existe deja la cantidad normal y crea un espacio en el mapa
            }
        }
        System.out.println("=== TOP 3 CELULARES MÁS VENDIDOS ===");
        conteo.entrySet().stream()
            //entrySet abre el map para el stream
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(3)
            .forEach(e -> System.out.println(e.getKey() + " -> " + e.getValue() + " vendidos"));
            //e: representa cada entrada o cada clave valor del mapa
    }
}
