package Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Modelo.Administrador;

public class AdministradorDaoSQL implements AdministradorDao {

    @Override
    public void guardar(Administrador admin) {
        String sqlPersona = "{call guardar_persona(?, ?, ?, ?, ?, ?)}";
        String sqlAdmin = "{call guardar_administrador(?)}";

        try (Connection con = ConexionDB.getDatos().getConexion()) {

            try (CallableStatement cs = con.prepareCall(sqlPersona)) {
                cs.setInt(1, admin.getId());
                cs.setString(2, admin.getNombre());
                cs.setString(3, admin.getIdentificacion());
                cs.setString(4, admin.getCorreo());
                cs.setString(5, admin.getTelefono());
                cs.setString(6, admin.getPassword());
                cs.execute();
            }

            try (CallableStatement cs = con.prepareCall(sqlAdmin)) {
                cs.setInt(1, admin.getId());
                cs.execute();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar administrador", e);
        }
    }

    @Override
    public Administrador buscarPorId(int id) {
        String sql = "{call buscar_persona_por_id(?)}";
        try (Connection con = ConexionDB.getDatos().getConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next() && "ADMIN".equals(rs.getString("rol"))) {
                    return mapear(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar administrador", e);
        }
    }

    @Override
    public Administrador buscarPorCorreo(String correo) {
        String sql = "{call buscar_persona_por_correo(?)}";
        try (Connection con = ConexionDB.getDatos().getConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, correo);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next() && "ADMIN".equals(rs.getString("rol"))) {
                    return mapear(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar administrador", e);
        }
    }

    @Override
    public List<Administrador> listarTodos() {
        String sql = "{call listar_administradores()}";
        List<Administrador> lista = new ArrayList<>();
        try (Connection con = ConexionDB.getDatos().getConexion();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar administradores", e);
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
            throw new RuntimeException("Error al comprobar administrador", e);
        }
    }

    private Administrador mapear(ResultSet rs) throws SQLException {
        return new Administrador(
            rs.getInt("id"),
            rs.getString("nombre"),
            rs.getString("identificacion"),
            rs.getString("correo"),
            rs.getString("telefono"),
            rs.getString("password")
        );
    }
}