package Vista;

import Modelo.ReporteVenta;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DatosVenta {

    private List<ReporteVenta> ventas = new ArrayList<>();
    private final String nombreArchivo = "ventas.dat";

    public DatosVenta() {
        cargar();
    }

    @SuppressWarnings("unchecked")
    public void cargar() {
        File archivo = new File(nombreArchivo);

        if (archivo.exists()) {
            try (ObjectInputStream ois =
                    new ObjectInputStream(
                        new FileInputStream(archivo))) {

                ventas = (List<ReporteVenta>) ois.readObject();

            } catch (IOException | ClassNotFoundException e) {
                System.out.println(
                    "Error al cargar ventas: " + e.getMessage());
            }
        }
    }

    public void reemplazar() {
        try (ObjectOutputStream oos =
                new ObjectOutputStream(
                    new FileOutputStream(nombreArchivo))) {

            oos.writeObject(ventas);

        } catch (IOException e) {
            System.out.println(
                "Error al guardar ventas: " + e.getMessage());
        }
    }

    public void guardarVentas(List<ReporteVenta> nuevasVentas) {
        this.ventas = new ArrayList<>(nuevasVentas);
        reemplazar();
    }

    public List<ReporteVenta> obtenerVentas() {
        cargar();
        return new ArrayList<>(ventas);
    }
}
