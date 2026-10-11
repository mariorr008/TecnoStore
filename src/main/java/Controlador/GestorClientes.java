package controlador;

import Dao.ClienteDao;
import java.util.List;
import Modelo.Cliente;
import Modelo.GenerarId;
import Modelo.ClienteNoEncontradoException;
import Modelo.DatosInvalidosException;

public class GestorClientes {

    private final ClienteDao clienteDAO;

    public GestorClientes(ClienteDao clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public void registrar(Cliente cliente) {
        if (clienteDAO.existeIdentificacion(cliente.getIdentificacion())) {
            throw new DatosInvalidosException("La identificación ya está registrada");
        }
        if (clienteDAO.existeCorreo(cliente.getCorreo())) {
            throw new DatosInvalidosException("El correo ya está registrado");
        }

        int id;
        do {
            id = GenerarId.generar();
        } while (clienteDAO.existe(id));

        Cliente nuevo = new Cliente(
                id,
                cliente.getNombre(),
                cliente.getIdentificacion(),
                cliente.getCorreo(),
                cliente.getTelefono(),
                cliente.getPassword()
        );
        clienteDAO.guardar(nuevo);
    }

    public void modificar(Cliente cliente) {
        if (!clienteDAO.existe(cliente.getId())) {
            throw new ClienteNoEncontradoException("No existe el cliente");
        }
        clienteDAO.guardar(cliente);
    }

    public void eliminar(int id) {
        if (!clienteDAO.existe(id)) {
            throw new ClienteNoEncontradoException("No existe el cliente");
        }
        clienteDAO.eliminar(id);
    }

    public Cliente buscarPorId(int id) {
        Cliente c = clienteDAO.buscarPorId(id);
        if (c == null) {
            throw new ClienteNoEncontradoException("No existe el cliente con id " + id);
        }
        return c;
    }

    public Cliente buscarPorCorreo(String correo) {
        return clienteDAO.buscarPorCorreo(correo);
    }

    public List<Cliente> listarTodos() {
        return clienteDAO.listarTodos();
    }
}