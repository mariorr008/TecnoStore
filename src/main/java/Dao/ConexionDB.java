package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static final String URL = "jdbc:mysql://localhost:3306/TecnoStoreMario";
    private static final String USER = "root";
    private static final String PASS = "Marioal8.";

    private static ConexionDB datos;

    private ConexionDB() {
        // nadie puede hacer new ConexionDB() por ser privado
    }

    public static ConexionDB getInstancia() {
        if (datos == null) {
            datos = new ConexionDB();
        }
        return datos;
    }

    public Connection getConexion() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos", e);
        }
    }
}