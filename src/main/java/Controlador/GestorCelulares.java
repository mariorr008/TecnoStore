package Controlador;

import Dao.CelularDao;
import Dao.MarcaDao;
import java.util.List;
import Modelo.Celular;
import Modelo.GenerarId;
import Modelo.Marca;
import Modelo.CelularNoEncontradoException;
import Modelo.DatosInvalidosException;

public class GestorCelulares {

    private final CelularDao celularDAO;
    private final MarcaDao marcaDAO;

    public GestorCelulares(CelularDao celularDAO, MarcaDao marcaDAO) {
        this.celularDAO = celularDAO;
        this.marcaDAO = marcaDAO;
    }

    public void registrar(Celular celular) {
        if (celular.getPrecio() <= 0) {
            throw new DatosInvalidosException("El precio debe ser positivo");
        }
        if (celular.getStock() < 0) {
            throw new DatosInvalidosException("El stock no puede ser negativo");
        }

        int id;
        do {
            id = GenerarId.generar();
        } while (celularDAO.existe(id));

        // Como Celular tiene id final, creamos uno nuevo con el id generado
        Celular nuevo = new Celular(
            id,
            celular.getMarca(),
            celular.getModelo(),
            celular.getSistemaOp(),
            celular.getGama(),
            celular.getPrecio(),
            celular.getStock()
        );
        celularDAO.guardar(nuevo);
    }

    public void modificar(Celular celular) {
        if (!celularDAO.existe(celular.getId())) {
            throw new CelularNoEncontradoException("No existe el celular con id " + celular.getId());
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
}