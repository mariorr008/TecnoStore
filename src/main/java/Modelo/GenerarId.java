package Modelo;

import java.util.Random;

public class GenerarId {
    private static final Random random = new Random();
    
    public static int generar() {
        return 10000 + random.nextInt(90000);
    }
}
