package modelo;

public class CelularNoEncontradoException extends RuntimeException {
    public CelularNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}