package Controlador;

import Dao.CelularDaoSQL;
import Dao.MarcaDaoSQL;
import java.util.List;
import Modelo.CategoriaGama;
import Modelo.Celular;
import Modelo.Marca;
import Modelo.SistemaOperativo;
import Modelo.CelularNoEncontradoException;
import Modelo.DatosInvalidosException;
import Vista.IngresoCelular;
import Vista.MenuCelulares;
import Vista.MostrarDatos;
import Vista.Validaciones;

public class ControladorCelulares {

    private final CelularDaoSQL celularDAO = new CelularDaoSQL();
    private final MarcaDaoSQL marcaDAO = new MarcaDaoSQL();
    private final GestorCelulares gestor = new GestorCelulares(celularDAO, marcaDAO);

    private final Validaciones v;
    private final MenuCelulares menu;
    private final IngresoCelular ingreso;
    private final MostrarDatos mostrar;

    public ControladorCelulares(Validaciones v) {
        this.v = v;
        this.menu = new MenuCelulares(v);
        this.ingreso = new IngresoCelular(v);
        this.mostrar = new MostrarDatos();
    }

    public void iniciar() {
        int op;
        do {
            op = menu.mostrarMenuCelulares();
            switch (op) {
                case 1 -> registrar();
                case 2 -> mostrar.mostrarCelulares(gestor.listarTodos());
                case 3 -> buscar();
                case 4 -> modificar();
                case 5 -> eliminar();
                case 6 -> filtrarPorGama();
                case 7 -> gestionarMarcas();
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción inválida");
            }
        } while (op != 0);
    }

    // ============ CELULAR ============
    private void registrar() {
        try {
            List<Marca> marcas = gestor.listarMarcas();
            if (marcas.isEmpty()) {
                System.out.println("No hay marcas. Registre una primero.");
                return;
            }
            mostrar.mostrarMarcas(marcas);
            int idMarca = ingreso.pedirIdMarca();
            Marca marca = gestor.buscarMarcaPorId(idMarca);

            String modelo = ingreso.pedirModelo();
            SistemaOperativo so = ingreso.pedirSistemaOperativo();
            CategoriaGama gama = ingreso.pedirGama();
            double precio = ingreso.pedirPrecio();
            int stock = ingreso.pedirStock();

            gestor.registrar(marca, modelo, so, gama, precio, stock);
            System.out.println("Celular registrado correctamente");
        } catch (DatosInvalidosException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void buscar() {
        try {
            int id = ingreso.pedirIdCelular();
            Celular c = gestor.buscarPorId(id);
            System.out.println(c);
        } catch (CelularNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void modificar() {
        try {
            int id = ingreso.pedirIdCelular();
            Celular c = gestor.buscarPorId(id);
            System.out.println("Datos actuales: " + c);

            String modelo = ingreso.pedirModelo();
            SistemaOperativo so = ingreso.pedirSistemaOperativo();
            CategoriaGama gama = ingreso.pedirGama();
            double precio = ingreso.pedirPrecio();
            int stock = ingreso.pedirStock();

            c.setModelo(modelo);
            c.setSistemaOp(so);
            c.setGama(gama);
            c.setPrecio(precio);
            c.setStock(stock);

            gestor.modificar(c);
            System.out.println("Celular modificado");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminar() {
        try {
            int id = ingreso.pedirIdCelular();
            gestor.eliminar(id);
            System.out.println("Celular eliminado");
        } catch (CelularNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void filtrarPorGama() {
        System.out.println("Gama a filtrar: 1. ALTA  2. MEDIA  3. BAJA");
        int op = v.pedirEntero("Opción: ");
        String gama = switch (op) {
            case 1 -> "ALTA";
            case 2 -> "MEDIA";
            case 3 -> "BAJA";
            default -> "ALTA";
        };
        mostrar.mostrarCelulares(gestor.listarPorGama(gama));
    }

    // ============ MARCA ============
    private void gestionarMarcas() {
        int op;
        do {
            op = menu.mostrarMenuMarcas();
            switch (op) {
                case 1 -> registrarMarca();
                case 2 -> mostrar.mostrarMarcas(gestor.listarMarcas());
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción inválida");
            }
        } while (op != 0);
    }

    private void registrarMarca() {
        try {
            String nombre = ingreso.pedirNombreMarca();
            gestor.registrarMarca(nombre);
            System.out.println("Marca registrada");
        } catch (DatosInvalidosException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}