package Controlador;

import Dao.VentaDao;
import Modelo.Venta;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReporteVentasPorMes implements StrategyReporte{

    private final VentaDao ventaDAO;

    public ReporteVentasPorMes(VentaDao ventaDAO) {
        this.ventaDAO = ventaDAO;
    }
    
    @Override
    public void generar() {
        List<Venta> ventas = ventaDAO.listarTodas();

        Map<YearMonth, Double> porMes = ventas.stream()
            .collect(Collectors.groupingBy(v -> YearMonth.from(v.getFecha()),
            //Agrupa todas las ventas por su año y mes
            Collectors.summingDouble(Venta::getTotal)
            //Suma el total de todas las ventas de su año y mes   
            ));
        
        System.out.println("=== VENTAS TOTALES POR MES ===");
        porMes.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(e -> System.out.println(e.getKey() + " → $" + e.getValue()));
    }
    
}
