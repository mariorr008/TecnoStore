package Controlador;

import Dao.CelularDao;
import Modelo.CategoriaGama;
import Modelo.Celular;
import Modelo.GenerarId;
import Modelo.Marca;
import Modelo.SistemaOperativo;
import Modelo.DatosInvalidosException;

public class FactoryCelular {

    public static Celular crear(Marca marca, String modelo,
                                SistemaOperativo so, CategoriaGama gama,
                                double precio, int stock,
                                CelularDao dao) {

        if (precio <= 0) {
            throw new DatosInvalidosException("El precio debe ser positivo");
        }
        if (stock < 0) {
            throw new DatosInvalidosException("El stock no puede ser negativo");
        }

        int id;
        do {
            id = GenerarId.generar();
        } while (dao.existe(id));

        return new Celular(id, marca, modelo, so, gama, precio, stock);
    }
}