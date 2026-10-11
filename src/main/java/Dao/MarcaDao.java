package Dao;

import Modelo.Marca;
import java.util.List;

public interface MarcaDao {
    void guardar(Marca marca);
    Marca buscarPorId(int id);
    Marca buscarPorNombre(String nombre);
    List<Marca> listarTodas();
    boolean existe(int id);
}
