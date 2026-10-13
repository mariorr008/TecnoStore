package Dao;

import Modelo.Marca;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarcaDaoSQL implements MarcaDao{

    @Override
    public void guardar(Marca marca) {
        String sql = "{call guardar_marca(?, ?)}";
        System.out.println(">>> Intentando guardar: " + marca.getId() + " - " + marca.getNombreMarca());
        try (Connection con = ConexionDB.getDatos().getConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, marca.getId());
            cs.setString(2, marca.getNombreMarca());
            cs.execute();
            System.out.println(">>> Guardado OK");

        } catch (SQLException e) {
            System.out.println(">>> ERROR: " + e.getMessage());
            throw new RuntimeException("Error al guardar marca", e);
        }
    }

    @Override
    public Marca buscarPorId(int id) {
        String sql = "{call buscar_marca_por_id(?)}";
        try (Connection con = ConexionDB.getDatos().getConexion();
             CallableStatement cs = con.prepareCall(sql)) {
            
            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()){
                if (rs.next()) {
                    return mapear(rs);
                }
                return null;
            }
        }catch (SQLException e){
            throw new RuntimeException("Error al buscar marca", e);
        }    
    }

    @Override
    public Marca buscarPorNombre(String nombre) {
        String sql = "{call buscar_marca_por_nombre(?)}";
        try (Connection con = ConexionDB.getDatos().getConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, nombre);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar marca", e);
        }
    }

    @Override
    public List<Marca> listarTodas() {
        String sql = "{call listar_marcas()}";
        List<Marca> lista = new ArrayList<>();
        try (Connection con = ConexionDB.getDatos().getConexion();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar marcas", e);
        }
    }

    @Override
    public boolean existe(int id) {
        String sql = "{call existe_marca(?)}";
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
            throw new RuntimeException("Error al comprobar marca", e);
        }
    }
    
    private Marca mapear(ResultSet rs) throws SQLException {
        return new Marca(rs.getInt("id"), rs.getString("nombre_marca"));
    }
}
