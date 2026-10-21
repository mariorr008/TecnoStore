package Modelo;

import java.io.Serializable;
import java.util.List;

public class ReporteVenta implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int id;
    private final String fecha;
    private final String cliente;
    private final List<String> productos;
    private final double total;

    public ReporteVenta(int id, String fecha, String cliente,
                        List<String> productos, double total) {
        this.id = id;
        this.fecha = fecha;
        this.cliente = cliente;
        this.productos = productos;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public String getCliente() {
        return cliente;
    }

    public List<String> getProductos() {
        return productos;
    }

    public double getTotal() {
        return total;
    }
}
