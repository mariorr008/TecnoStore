package com.practica.tecnostore;

import Controlador.ControladorPrincipal;
import Dao.AdministradorDaoSQL;
import Modelo.Administrador;
import Modelo.GenerarId;

public class TecnoStore {

    public static void main(String[] args) {
        crearAdminPorDefecto();

        ControladorPrincipal controlador = new ControladorPrincipal();
        controlador.iniciar();
    }

    private static void crearAdminPorDefecto() {
        AdministradorDaoSQL adminDAO = new AdministradorDaoSQL();

        if (!adminDAO.listarTodos().isEmpty()) {
            return;
        }

        int id;
        do {
            id = GenerarId.generar();
        } while (adminDAO.existe(id));

        Administrador admin = new Administrador(
            id,
            "Admin Principal",
            "1000000000",
            "admin@tecnostore.com",
            "3001234567",
            "admin123"
        );
        adminDAO.guardar(admin);

        System.out.println("Admin por defecto creado:");
        System.out.println("  Correo: admin@tecnostore.com");
        System.out.println("  Password: admin123");
    }
}