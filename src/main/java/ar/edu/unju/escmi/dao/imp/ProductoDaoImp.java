package ar.edu.unju.escmi.dao.imp;

import jakarta.persistence.EntityManager;
import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IProductoDao;
import ar.edu.unju.escmi.entities.Producto;

public class ProductoDaoImp implements IProductoDao {
    
    @Override
    public void guardarProducto(Producto producto) {    
        EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(producto);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction() != null)
                em.getTransaction().rollback();
            System.out.println("Error al guardar el producto: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public void borrarProducto(Producto producto) {
        EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();
        try {
            em.getTransaction().begin();
            Producto prod = em.find(Producto.class, producto.getId());
            if (prod != null) {
                prod.setEstado(false);
                em.merge(prod);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction() != null)
                em.getTransaction().rollback();
            System.out.println("Error al borrar el producto: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public double obtenerPrecioPorId(Long idProd) {
        EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();
        double precio = 0;
        try {
            Producto producto = em.find(Producto.class, idProd);
            if (producto != null && producto.isEstado()) {
                precio = producto.getPrecioUnitario();
            }
        } catch (Exception e) {
            System.out.println(" Error al obtener el precio: " + e.getMessage());
        } finally {
            em.close();
        }
        return precio;
    }

    @Override
    public void modificarPrecio(Long idProd, double nuevoPrecio) {
        EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();
        try {
            em.getTransaction().begin();
            Producto producto = em.find(Producto.class, idProd);
            if (producto != null && producto.isEstado()) {
                producto.setPrecioUnitario(nuevoPrecio);
                em.merge(producto);
            } else {
                System.out.println("Producto no encontrado o inactivo.");
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction() != null)
                em.getTransaction().rollback();
            System.out.println("Error al modificar el precio: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public Producto buscarPorId(Long id) {
        EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();
        Producto producto = null;
        try {
            producto = em.find(Producto.class, id);
        } catch (Exception e) {
            System.out.println("Error al buscar el producto: " + e.getMessage());
        } finally {
            em.close();
        }
        return producto;
    }
    

}