package Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Modelo.Celular;
import Modelo.Cliente;
import Modelo.ItemVenta;
import Modelo.Venta;

public class VentaDaoSQL implements VentaDao{
    
    private final CelularDao celularDAO = new CelularDaoSQL();
    private final ClienteDao clienteDAO = new ClienteDaoSQL();

    @Override
    public void guardar(Venta venta) {
        String sqlVenta = "{call guardar_venta(?, ?, ?)}";
        String sqlDetalle = "{call guardar_detalle_venta(?, ?, ?, ?, ?, ?)}";

        try (Connection con = ConexionDB.getDatos().getConexion()) {

            // 1. Guardar la venta
            try (CallableStatement cs = con.prepareCall(sqlVenta)) {
                cs.setInt(1, venta.getId());
                cs.setDouble(2, venta.getTotal());
                cs.setInt(3, venta.getCliente().getId());
                cs.execute();
            }

            // 2. Guardar cada ítem
            for (ItemVenta item : venta.getItems()) {
                try (CallableStatement cs = con.prepareCall(sqlDetalle)) {
                    cs.setInt(1, item.getId());
                    cs.setInt(2, venta.getId());
                    cs.setInt(3, item.getCelular().getId());
                    cs.setInt(4, item.getCantidad());
                    cs.setDouble(5, item.getPrecioUnitario());
                    cs.setDouble(6, item.getSubtotal());
                    cs.execute();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar venta", e);
        }
    }


    @Override
    public Venta bucarPorId(int id) {
        String sql = "{call buscar_venta_por_id(?)}";
        try (Connection con = ConexionDB.getDatos().getConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapearVenta(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar venta", e);
        }
    }

    @Override
    public List<Venta> listarTodas() {
        String sql = "{call listar_ventas()}";
        List<Venta> lista = new ArrayList<>();
        try (Connection con = ConexionDB.getDatos().getConexion();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearVenta(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar ventas", e);
        }
    }

    @Override
    public List<Venta> listarPorClientes(int id_Cliente) {
        String sql = "{call listar_ventas_por_cliente(?)}";
        List<Venta> lista = new ArrayList<>();
        try (Connection con = ConexionDB.getDatos().getConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id_Cliente);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearVenta(rs));
                }
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar ventas por cliente", e);
        }
    }

    @Override
    public boolean existe(int id) {
        String sql = "{call existe_venta(?)}";
        try (Connection con = ConexionDB.getDatos().getConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) return rs.getInt("total") > 0;
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al comprobar venta", e);
        }
    }

    
        // Carga los ítems de una venta
    public List<ItemVenta> listarItemsDeVenta(int idVenta) {
        String sql = "{call listar_detalle_por_venta(?)}";
        List<ItemVenta> items = new ArrayList<>();
        try (Connection con = ConexionDB.getDatos().getConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, idVenta);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    items.add(mapearItem(rs));
                }
            }
            return items;
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar items de venta", e);
        }
    }

    private Venta mapearVenta(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int idCliente = rs.getInt("id_cliente");
        Cliente cliente = clienteDAO.buscarPorId(idCliente);

        Venta venta = new Venta(id, cliente);
        // Si se necesitan los items se llama a listarItemsDeVenta(id).
        return venta;
    }

    private ItemVenta mapearItem(ResultSet rs) throws SQLException {
        // El JOIN del detalle no trae todos los campos del celular.
        // lo mejor es buscar el celular por ID.
        Celular celular = celularDAO.buscarPorId(rs.getInt("id_celular"));

        return new ItemVenta(
            rs.getInt("id"),
            celular,
            rs.getInt("cantidad")
        );
    }
    
}