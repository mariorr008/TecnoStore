package Vista;

import Modelo.CategoriaGama;
import Modelo.DatosInvalidosException;
import Modelo.SistemaOperativo;

public class IngresoCelular {

    private final Validaciones v;

    public IngresoCelular(Validaciones v) {
        this.v = v;
    }

    // ============ MARCA ============
    public String pedirNombreMarca() {
        return v.pedirSoloLetras("Nombre de la marca: ");
    }

    // ============ CELULAR ============
    public String pedirModelo() {
        return v.pedirTexto("Modelo: ");
    }

    public SistemaOperativo pedirSistemaOperativo() {
        int op;

        do {
            System.out.println("""
                    =====SISTEMA OPERATIVO=====
                    1. iOS
                    2. Android
                    3. KaiOS
                    """);

            op = v.pedirEntero("Opción: ");

            if (op < 1 || op > 3) {
                System.out.println("Opción inválida. Inténtelo nuevamente.");
            }

        } while (op < 1 || op > 3);

        return switch (op) {
            case 1 -> SistemaOperativo.iOS;
            case 2 -> SistemaOperativo.Android;
            case 3 -> SistemaOperativo.KaiOS;
            default -> throw new DatosInvalidosException("Opción inválida");
        };
    }

    public CategoriaGama pedirGama() {
        int op;

        do {
            System.out.println("""
                    =====SISTEMA OPERATIVO=====
                    1. ALTA
                    2. MEDIA
                    3. BAJA
                    """);

            op = v.pedirEntero("Opción: ");

            if (op < 1 || op > 3) {
                System.out.println("Opción inválida. Inténtelo nuevamente.");
            }

        } while (op < 1 || op > 3);

        return switch (op) {
            case 1 -> CategoriaGama.ALTA;
            case 2 -> CategoriaGama.MEDIA;
            case 3 -> CategoriaGama.BAJA;
            default -> throw new DatosInvalidosException("Opción inválida");
        };
    }

    public double pedirPrecio() {
        return v.pedirDoublePositivo("Precio: ");
    }

    public int pedirStock() {
        return v.pedirEnteroPositivo("Stock: ");
    }
}