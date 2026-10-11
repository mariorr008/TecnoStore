package Dao;

import Modelo.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDaoSQL implements ClienteDao{

    @Override
    public void guardar(Cliente cliente) {
        String sqlPersona = "{call guardar_persona(?, ?, ?, ?, ?, ?)}";
        String sqlCliente = "{call guardar_cliente(?)}";

        try (Connection con = ConexionDB.getDatos().getConexion()) {

            try (CallableStatement cs = con.prepareCall(sqlPersona)) {
                cs.setInt(1, cliente.getId());
                cs.setString(2, cliente.getNombre());
                cs.setString(3, cliente.getIdentificacion());
                cs.setString(4, cliente.getCorreo());
                cs.setString(5, cliente.getTelefono());
                cs.setString(6, cliente.getPassword());
                cs.execute();
            }

            try (CallableStatement cs = con.prepareCall(sqlCliente)) {
                cs.setInt(1, cliente.getId());
                cs.execute();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar cliente", e);
        }
    }

    @Override
    public Cliente buscarPorId(int id) {
        String sql = "{call buscar_persona_por_id(?)}";
        try (Connection con = ConexionDB.getDatos().getConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next() && "CLIENTE".equals(rs.getString("rol"))) {
                    return mapear(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar cliente", e);
        }
    }

    @Override
    public Cliente buscarPorCorreo(String correo) {
        String sql = "{call buscar_persona_por_correo(?)}";
        try (Connection con = ConexionDB.getDatos().getConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, correo);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next() && "CLIENTE".equals(rs.getString("rol"))) {
                    return mapear(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar cliente", e);
        }
    }

    @Override
    public List<Cliente> listarTodos() {
        String sql = "{call listar_clientes()}";
        List<Cliente> lista = new ArrayList<>();
        try (Connection con = ConexionDB.getDatos().getConexion();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar clientes", e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "{call eliminar_persona(?)}";
        try (Connection con = ConexionDB.getDatos().getConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar cliente", e);
        }
    }

    @Override
    public boolean existe(int id) {
        String sql = "{call existe_persona(?)}";
        try (Connection con = ConexionDB.getDatos().getConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) return rs.getInt("total") > 0;
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al comprobar cliente", e);
        }
    }

    @Override
    public boolean existeIdentificacion(String identificacion) {
        String sql = "{call existe_identificacion(?)}";
        try (Connection con = ConexionDB.getDatos().getConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, identificacion);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) return rs.getInt("total") > 0;
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al comprobar identificación", e);
        }
    }

    @Override
    public boolean existeCorreo(String correo) {
        String sql = "{call existe_correo(?)}";
        try (Connection con = ConexionDB.getDatos().getConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, correo);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) return rs.getInt("total") > 0;
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al comprobar correo", e);
        }
    }

    private Cliente mapear(ResultSet rs) throws SQLException {
        return new Cliente(
            rs.getInt("id"),
            rs.getString("nombre"),
            rs.getString("identificacion"),
            rs.getString("correo"),
            rs.getString("telefono"),
            rs.getString("password")
        );
    }
}