package Controlador;

import Dao.ClienteDaoSQL;
import Modelo.Administrador;
import Modelo.Cliente;
import Modelo.ClienteNoEncontradoException;
import Vista.IngresoDatos;
import Vista.MenuAdmin;
import Vista.MostrarDatos;
import Vista.Validaciones;

public class ControladorAdmin {

    private final ClienteDaoSQL clienteDAO = new ClienteDaoSQL();
    private final GestorClientes gestor = new GestorClientes(clienteDAO);

    private final MenuAdmin menu;
    private final IngresoDatos ingreso;
    private final MostrarDatos mostrar;

    private final ControladorCelulares controladorCelulares;
    private final ControladorVentas controladorVentas;

    public ControladorAdmin(Validaciones v) {
        this.menu = new MenuAdmin(v);
        this.ingreso = new IngresoDatos(v);
        this.mostrar = new MostrarDatos();

        this.controladorCelulares = new ControladorCelulares(v);
        this.controladorVentas = new ControladorVentas(v);
    }

    public void iniciar(Administrador admin) {
        int op;
        do {
            op = menu.mostrarMenuAdmin();
            switch (op) {
                case 1 -> controladorCelulares.iniciar();
                case 2 -> gestionarClientes();
                case 3 -> controladorVentas.iniciarVentas();
                case 4 -> controladorVentas.iniciarReportes();
                case 5 -> controladorVentas.generarArchivo();
                case 0 -> System.out.println("Cerrando sesión...");
                default -> System.out.println("Opción inválida");
            }
        } while (op != 0);
    }

    // ============ GESTIÓN CLIENTES ============
    private void gestionarClientes() {
        int op;
        do {
            op = menu.mostrarMenuClientes();
            switch (op) {
                case 1 -> mostrar.mostrarClientes(gestor.listarTodos());
                case 2 -> buscarCliente();
                case 3 -> eliminarCliente();
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción inválida");
            }
        } while (op != 0);
    }

    private void buscarCliente() {
        try {
            int id = ingreso.pedirIdCliente();
            Cliente c = gestor.buscarPorId(id);
            System.out.println(c);
        } catch (ClienteNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminarCliente() {
        try {
            int id = ingreso.pedirIdCliente();
            gestor.eliminar(id);
            System.out.println("Cliente eliminado");
        } catch (ClienteNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}