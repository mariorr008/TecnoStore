package Controlador;

import Dao.CelularDao;
import Modelo.Celular;
import java.util.List;

public class ReporteStockBajo implements StrategyReporte{

    private final CelularDao celularDAO;

    public ReporteStockBajo(CelularDao celularDAO) {
        this.celularDAO = celularDAO;
    }
    
    @Override
    public void generar() {
        List<Celular> todos = celularDAO.listarTodos();

        List<Celular> stockBajo = todos.stream()
            .filter(c -> c.getStock() < 5)
            .toList();

        System.out.println("=== CELULARES CON STOCK BAJO (<5) ===");
        if (stockBajo.isEmpty()) {
            System.out.println("No hay celulares con stock bajo");
        } else {
            for (Celular celular : stockBajo) {
                System.out.println(celular);
                //stockBajo.forEach(System.out::println);

            }
        }
    }  
}
