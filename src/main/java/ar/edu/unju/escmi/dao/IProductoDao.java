package ar.edu.unju.escmi.dao;

import ar.edu.unju.escmi.entities.Producto;
import java.util.List;
import java.util.Scanner;

public interface IProductoDao {
    void guardarProducto(Producto producto);
    void borrarProducto(Producto producto);
    double obtenerPrecioPorId(Long idProd);
    void modificarPrecio(Long idProd, double nuevoPrecio);

    Producto buscarPorId(Long id);
    List<Producto> obtenerTodos();
    void eliminarLogico(Long id);

    void altaProducto(Scanner sc);
    void modificarPrecio(Scanner sc);
    void eliminarLogicoProducto(Scanner sc);
}
