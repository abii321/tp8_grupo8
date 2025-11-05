package ar.edu.unju.escmi.dao;

import java.util.List;
import ar.edu.unju.escmi.entities.Producto;

public interface IProductoDao {
    void guardar(Producto producto);
    void modificar(Producto producto);
    Producto buscarPorId(Long id);
    List<Producto> obtenerTodos();
    void eliminarLogico(Long id);
}
