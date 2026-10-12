package Vista;

import java.util.Scanner;

public class Validaciones {

    private final Scanner X = new Scanner(System.in);

    // ============ NÚMEROS ============
    public int pedirEntero(String mensaje) {
        System.out.print(mensaje);
        while (!X.hasNextInt()) {
            System.out.println("Error: se espera un número entero");
            X.next();
            System.out.print(mensaje);
        }
        int valor = X.nextInt();
        X.nextLine();
        return valor;
    }

    public int pedirEnteroPositivo(String mensaje) {
        int valor;
        do {
            valor = pedirEntero(mensaje);
            if (valor <= 0) {
                System.out.println("Debe ser mayor que 0");
            }
        } while (valor <= 0);
        return valor;
    }

    public double pedirDouble(String mensaje) {
        System.out.print(mensaje);
        while (!X.hasNextDouble()) {
            System.out.println("Error: se espera un número decimal");
            X.next();
            System.out.print(mensaje);
        }
        double valor = X.nextDouble();
        X.nextLine();
        return valor;
    }

    public double pedirDoublePositivo(String mensaje) {
        double valor;
        do {
            valor = pedirDouble(mensaje);
            if (valor <= 0) {
                System.out.println("Debe ser mayor que 0");
            }
        } while (valor <= 0);
        return valor;
    }

    // ============ TEXTO ============
    public String pedirTexto(String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = X.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("No puede estar vacío");
            }
        } while (texto.isEmpty());
        return texto;
    }

    public String pedirSoloLetras(String mensaje) {
        String texto;
        do {
            texto = pedirTexto(mensaje);
            if (!texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                //matches: comprueba si un texto cumple un patrón específico
                System.out.println("Solo se permiten letras y espacios");
                texto = "";
            }
        } while (texto.isEmpty());
        return texto;
    }

    // ============ FORMATOS ============
    public String pedirCorreo(String mensaje) {
        String correo;
        do {
            correo = pedirTexto(mensaje);
            if (!correo.matches("^[\\w.+-]+@[\\w-]+\\.[\\w.]+$")) {
                System.out.println("Correo inválido (ej: nombre@dominio.com)");
                correo = "";
            }
        } while (correo.isEmpty());
        return correo;
    }

    public String pedirIdentificacion(String mensaje) {
        String id;
        do {
            id = pedirTexto(mensaje);
            if (!id.matches("\\d{6,10}")) {
                System.out.println("Identificación inválida (6 a 10 dígitos)");
                id = "";
            }
        } while (id.isEmpty());
        return id;
    }

    public String pedirTelefono(String mensaje) {
        String tel;
        do {
            tel = pedirTexto(mensaje);
            if (!tel.matches("\\d{7,10}")) {
                System.out.println("Teléfono inválido (7 a 10 dígitos)");
                tel = "";
            }
        } while (tel.isEmpty());
        return tel;
    }

    public String pedirPassword(String mensaje) {
        String pass;
        do {
            pass = pedirTexto(mensaje);
            if (pass.length() < 4) {
                System.out.println("La contraseña debe tener al menos 4 caracteres");
                pass = "";
            }
        } while (pass.isEmpty());
        return pass;
    }

    // ============ CONFIRMACIÓN ============
    public boolean pedirSiNo(String mensaje) {
        String resp;
        do {
            System.out.print(mensaje + " (s/n): ");
            resp = X.nextLine().trim().toLowerCase();
        } while (!resp.equals("s") && !resp.equals("n"));
        return resp.equals("s");
    }
}