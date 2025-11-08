package ar.edu.unju.escmi.dao;

import ar.edu.unju.escmi.entities.Producto;

public interface IProductoDao {
    void guardarProducto(Producto producto);
    void borrarProducto(Producto producto); 
    void modificarPrecio(Long idProd, double nuevoPrecio);
    double obtenerPrecioPorId(Long idProd);
    Producto buscarPorId(Long id);
}
