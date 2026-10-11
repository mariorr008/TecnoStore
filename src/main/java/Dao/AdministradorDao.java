package Dao;

import Modelo.Administrador;
import java.util.List;

public interface AdministradorDao {
    void guardar(Administrador admin);
    Administrador buscarPorId(int id);
    Administrador buscarPorCorreo(String correo);
    List<Administrador> listarTodos();
    boolean existe(int id);
}
