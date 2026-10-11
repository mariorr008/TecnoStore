package Dao;

import Modelo.Celular;
import java.util.List;

public interface CelularDao {
    void guardar(Celular celular);
    Celular buscarPorId(int id);
    List<Celular> listarTodos();
    List<Celular> listarPorGama(String gama);
    List<Celular> listarStockBajo();
    void eliminar(int id);
    boolean existe(int id);
    void actualizarStock(int id, int nuevoStock);
}
