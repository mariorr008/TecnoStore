package Modelo;

public class CelularNoEncontradoException extends RuntimeException {
    public CelularNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}