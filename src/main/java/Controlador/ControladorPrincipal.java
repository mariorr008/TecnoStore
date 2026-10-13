package Controlador;

import Dao.AdministradorDaoSQL;
import Dao.ClienteDaoSQL;
import Modelo.Administrador;    
import Modelo.Cliente;
import Modelo.DatosInvalidosException;
import Modelo.GenerarId;
import Vista.IngresoDatos;
import Vista.MenuPrincipal;
import Vista.Validaciones;

public class ControladorPrincipal {

    private final ClienteDaoSQL clienteDAO;
    private final AdministradorDaoSQL adminDAO;

    private final Validaciones v;
    private final MenuPrincipal menuPrincipal;
    private final IngresoDatos ingresoDatos;

    private final ControladorAdmin controladorAdmin;
    private final ControladorClientes controladorCliente;

    public ControladorPrincipal() {
        // DAOs
        this.clienteDAO = new ClienteDaoSQL();
        this.adminDAO = new AdministradorDaoSQL();

        // Vista
        this.v = new Validaciones();
        this.menuPrincipal = new MenuPrincipal(v);
        this.ingresoDatos = new IngresoDatos(v);

        // Sub-controladores
        this.controladorAdmin = new ControladorAdmin(v);
        this.controladorCliente = new ControladorClientes(v);
    }

    public void iniciar() {
        int op;
        do {
            op = menuPrincipal.mostrarMenuPrincipal();
            switch (op) {
                case 1 -> login();
                case 2 -> registrarCliente();
                case 3 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida");
            }
        } while (op != 3);
    }

    private void login() {
        String correo = ingresoDatos.pedirCorreoLogin();
        String password = ingresoDatos.pedirPasswordLogin();

        Cliente c = clienteDAO.buscarPorCorreo(correo);
        if (c != null && c.getPassword().equals(password)) {
            controladorCliente.iniciar(c);
            return;
        }

        Administrador a = adminDAO.buscarPorCorreo(correo);
        if (a != null && a.getPassword().equals(password)) {
            controladorAdmin.iniciar(a);
            return;
        }

        System.out.println("Correo o contraseña incorrectos");
    }

    private void registrarCliente() {
        try {
            Cliente c = ingresoDatos.pedirCliente();
            // Validaciones y guardado
            if (clienteDAO.existeIdentificacion(c.getIdentificacion())) {
                throw new DatosInvalidosException("La identificación ya está registrada");
            }
            if (clienteDAO.existeCorreo(c.getCorreo())) {
                throw new DatosInvalidosException("El correo ya está registrado");
            }
            int id;
            do { id = GenerarId.generar(); 
            } while (clienteDAO.existe(id));
            Cliente nuevo = new Cliente(id, c.getNombre(), c.getIdentificacion(),
                                        c.getCorreo(), c.getTelefono(), c.getPassword());
            clienteDAO.guardar(nuevo);
            System.out.println("Cliente registrado correctamente");
            
        } catch (DatosInvalidosException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}