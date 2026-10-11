package Dao;

import Modelo.Cliente;
import java.util.List;

public interface ClienteDao {
    void guardar(Cliente cliente);
    Cliente buscarPorId(int id);
    Cliente buscarPorCorreo(String correo);
    List<Cliente> listarTodos();
    void eliminar(int id);
    boolean existe(int id);
    boolean existeIdentificacion(String identificacion);
    boolean existeCorreo(String Correo);
}
