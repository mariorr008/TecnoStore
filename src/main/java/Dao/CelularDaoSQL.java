package Dao;

import Modelo.CategoriaGama;
import Modelo.Celular;
import Modelo.Marca;
import Modelo.SistemaOperativo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CelularDaoSQL implements CelularDao{

@Override
    public void guardar(Celular celular) {
        String sql = "{call guardar_celular(?, ?, ?, ?, ?, ?, ?)}";
        try (Connection con = ConexionDB.getDatos().getConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, celular.getId());
            cs.setInt(2, celular.getMarca().getId());
            cs.setString(3, celular.getModelo());
            cs.setString(4, celular.getSistemaOp().name());
            cs.setString(5, celular.getGama().name());
            cs.setDouble(6, celular.getPrecio());
            cs.setInt(7, celular.getStock());
            cs.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar celular", e);
        }
    }

    @Override
    public Celular buscarPorId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Celular> listarTodos() {
        String sql = "{call listar_celulares()}";
        List<Celular> lista = new ArrayList<>();
        try (Connection con = ConexionDB.getDatos().getConexion();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar celulares", e);
        }
    }

    @Override
    public List<Celular> listarPorGama(String gama) {
        String sql = "{call listar_celulares_por_gama(?)}";
        List<Celular> lista = new ArrayList<>();
        try (Connection con = ConexionDB.getDatos().getConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, gama);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar celulares por gama", e);
        }
    }

    @Override
    public List<Celular> listarStockBajo() {
        String sql = "{call listar_celulares_stock_bajo()}";
        List<Celular> lista = new ArrayList<>();
        try (Connection con = ConexionDB.getDatos().getConexion();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar celulares con stock bajo", e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "{call eliminar_celular(?)}";
        try (Connection con = ConexionDB.getDatos().getConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar celular", e);
        }
    }

    @Override
    public boolean existe(int id) {
        String sql = "{call existe_celular(?)}";
        try (Connection con = ConexionDB.getDatos().getConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al comprobar celular", e);
        }
    }

    @Override
    public void actualizarStock(int id, int nuevoStock) {
        String sql = "{call actualizar_stock_celular(?, ?)}";
        try (Connection con = ConexionDB.getDatos().getConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.setInt(2, nuevoStock);
            cs.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar stock", e);
        }
    }
    
    
    private Celular mapear(ResultSet rs) throws SQLException {
        // 1. Construyes la Marca con los datos de la fila
        Marca marca = new Marca(
            rs.getInt("id_marca"),
            rs.getString("nombre_marca")
        );

        // 2. Construyes el Celular con esa Marca y el resto de campos
        return new Celular(
            rs.getInt("id"),
            marca,
            rs.getString("modelo"),
            SistemaOperativo.valueOf(rs.getString("sistema_operativo")),
            CategoriaGama.valueOf(rs.getString("gama")),
            rs.getDouble("precio"),
            rs.getInt("stock")
        );
    }
}
