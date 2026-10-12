package Controlador;

import Dao.CelularDao;
import Dao.MarcaDao;
import java.util.List;
import Modelo.CategoriaGama;
import Modelo.Celular;
import Modelo.GenerarId;
import Modelo.Marca;
import Modelo.SistemaOperativo;
import Modelo.CelularNoEncontradoException;
import Modelo.DatosInvalidosException;

public class GestorCelulares {

    private final CelularDao celularDAO;
    private final MarcaDao marcaDAO;

    public GestorCelulares(CelularDao celularDAO, MarcaDao marcaDAO) {
        this.celularDAO = celularDAO;
        this.marcaDAO = marcaDAO;
    }

    // ============ CELULAR ============
    public void registrar(Marca marca, String modelo, SistemaOperativo so,
                          CategoriaGama gama, double precio, int stock) {

        Celular nuevo = FactoryCelular.crear(
            marca, modelo, so, gama, precio, stock, celularDAO
        );
        celularDAO.guardar(nuevo);
    }

    public void modificar(Celular celular) {
        if (!celularDAO.existe(celular.getId())) {
            throw new CelularNoEncontradoException(
                "No existe el celular con id " + celular.getId()
            );
        }
        if (celular.getPrecio() <= 0) {
            throw new DatosInvalidosException("El precio debe ser positivo");
        }
        if (celular.getStock() < 0) {
            throw new DatosInvalidosException("El stock no puede ser negativo");
        }
        celularDAO.guardar(celular);
    }

    public void eliminar(int id) {
        if (!celularDAO.existe(id)) {
            throw new CelularNoEncontradoException("No existe el celular con id " + id);
        }
        celularDAO.eliminar(id);
    }

    public Celular buscarPorId(int id) {
        Celular c = celularDAO.buscarPorId(id);
        if (c == null) {
            throw new CelularNoEncontradoException("No existe el celular con id " + id);
        }
        return c;
    }

    public List<Celular> listarTodos() {
        return celularDAO.listarTodos();
    }

    public List<Celular> listarPorGama(String gama) {
        return celularDAO.listarPorGama(gama);
    }

    public void actualizarStock(int id, int nuevoStock) {
        if (nuevoStock < 0) {
            throw new DatosInvalidosException("Stock no puede ser negativo");
        }
        celularDAO.actualizarStock(id, nuevoStock);
    }

    // ============ MARCA ============
    public void registrarMarca(String nombreMarca) {
        if (nombreMarca == null || nombreMarca.isBlank()) {
            throw new DatosInvalidosException("El nombre de la marca no puede estar vacío");
        }
        if (marcaDAO.buscarPorNombre(nombreMarca) != null) {
            throw new DatosInvalidosException("La marca ya existe");
        }

        int id;
        do {
            id = GenerarId.generar();
        } while (marcaDAO.existe(id));

        marcaDAO.guardar(new Marca(id, nombreMarca));
    }

    public List<Marca> listarMarcas() {
        return marcaDAO.listarTodas();
    }

    public Marca buscarMarcaPorId(int id) {
        Marca m = marcaDAO.buscarPorId(id);
        if (m == null) {
            throw new DatosInvalidosException("No existe la marca con id " + id);
        }
        return m;
    }
}