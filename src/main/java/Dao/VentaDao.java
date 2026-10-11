package Dao;

import Modelo.Venta;
import java.util.List;

public interface VentaDao {
    void guardar(Venta venta);
    Venta bucarPorId(int id);
    List<Venta> listarTodas();
    List<Venta> listarPorClientes(int id_Cliente);
    boolean existe(int id);
}
