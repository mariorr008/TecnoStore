package Modelo;

public class Celular {
    private final int id;
    private final Marca marca;
    private final String modelo;
    private SistemaOperativo sistemaOp;
    private CategoriaGama gama;
    private double precio;
    private int stock;

    public Celular(int id, Marca marca, String modelo, SistemaOperativo sistemaOp, CategoriaGama gama, double precio, int stock) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.sistemaOp = sistemaOp;
        this.gama = gama;
        this.precio = precio;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public Marca getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public SistemaOperativo getSistemaOp() {
        return sistemaOp;
    }

    public void setSistemaOp(SistemaOperativo sistemaOp) {
        this.sistemaOp = sistemaOp;
    }
    

    public CategoriaGama getGama() {
        return gama;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public void setGama(CategoriaGama gama) {
        this.gama = gama;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    public void prestar(int cantidad) {
        if (stock < cantidad){
            throw new StockInsuficienteException(
            "Stock insuficiente para " +marca+ " " +modelo);
        }
        stock-=cantidad;
    }

    @Override
    public String toString() {
        return """
               ID:      %s
               Marca:   %s
               Modelo:  %s
               Gama:    %s
               Precio:  %s
               Stock:   %s
               """.formatted(id,marca,modelo,gama,precio,stock);
    }
}
