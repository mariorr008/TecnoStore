package Modelo;

public class Marca {
    private final int id;
    private final String nombreMarca;

    public Marca(int id, String nombreMarca) {
        this.id = id;
        this.nombreMarca = nombreMarca;
    }

    public int getId() {
        return id;
    }

    public String getNombreMarca() {
        return nombreMarca;
    }

    @Override
    public String toString() {
        return """
               Marca:   %s
               """.formatted(nombreMarca);
    }
    
    
    
}
