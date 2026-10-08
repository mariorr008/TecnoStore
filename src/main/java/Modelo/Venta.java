package Modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venta {
    private final int id;
    private final LocalDateTime fecha;
    private double total;
    private final Cliente cliente;
    private final List<ItemVenta> items;

    public Venta(int id, Cliente cliente) {
        this.id = id;
        this.fecha = LocalDateTime.now();
        this.total = 0;
        this.cliente = cliente;
        this.items = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public double getTotal() {
        return total;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ItemVenta> getItems() {
        return items;
    }
        
    public void agregarItem(ItemVenta item){
        items.add(item);
            recalcularTotal();
    }
        
    private void recalcularTotal() {
        total = items.stream()
        .mapToDouble(ItemVenta::getSubtotal)
        .sum();
    }

    @Override
    public String toString() {
        return """
               =====VENTA=====
               ID:      %s
               Fecha:   %s
               Cliente: %s  
               Total    %s
               """.formatted(id,fecha,cliente,total);
    }
}
