package Controlador;

import Dao.CelularDaoSQL;
import Modelo.Cliente;
import Vista.MenuCliente;
import Vista.MostrarDatos;
import Vista.Validaciones;

public class ControladorClientes {

    private final CelularDaoSQL celularDAO = new CelularDaoSQL();
    private final GestorCelulares gestorCelulares = new GestorCelulares(celularDAO, new Dao.MarcaDaoSQL());

    private final MenuCliente menu;
    private final MostrarDatos mostrarDatos;

    private final ControladorVentas controladorVentas;

    public ControladorClientes(Validaciones v) {
        this.menu = new MenuCliente(v);
        this.mostrarDatos = new MostrarDatos();

        this.controladorVentas = new ControladorVentas(v);
    }

    public void iniciar(Cliente cliente) {
        int op;
        do {
            op = menu.mostrarMenuCliente();
            switch (op) {
                case 1 -> mostrarDatos.mostrarCelulares(gestorCelulares.listarTodos());
                case 2 -> controladorVentas.realizarCompra(cliente);
                case 3 -> controladorVentas.verHistorial(cliente);
                case 0 -> System.out.println("Cerrando sesión...");
                default -> System.out.println("Opción inválida");
            }
        } while (op != 0);
    }
}