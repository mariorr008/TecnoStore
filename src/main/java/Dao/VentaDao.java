package Dao;

import Modelo.ItemVenta;
import Modelo.Venta;
import java.util.List;

public interface VentaDao {
    void guardar(Venta venta);
    Venta bucarPorId(int id);
    List<Venta> listarTodas();
    List<Venta> listarPorCliente(int id_Cliente);
    List<ItemVenta> listarItemsDeVenta(int idVenta);
    boolean existe(int id);
}
