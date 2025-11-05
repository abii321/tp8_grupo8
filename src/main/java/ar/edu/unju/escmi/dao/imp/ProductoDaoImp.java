package ar.edu.unju.escmi.dao.imp;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IProductoDao;
import ar.edu.unju.escmi.entities.Producto;

public class ProductoDAOImp implements IProductoDao {

    private EntityManager em = EmfSingleton.getInstance().createEntityManager();

    @Override
    public void guardar(Producto producto) {
        try {
            em.getTransaction().begin();
            em.persist(producto);
            em.getTransaction().commit();
            System.out.println(" Producto guardado correctamente.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println(" Error al guardar el producto: " + e.getMessage());
        }
    }

    @Override
    public void modificar(Producto producto) {
        try {
            em.getTransaction().begin();
            em.merge(producto);
            em.getTransaction().commit();
            System.out.println("Producto modificado correctamente.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Error al modificar el producto: " + e.getMessage());
        }
    }

    @Override
    public Producto buscarPorId(Long id) {
        Producto producto = null;
        try {
            producto = em.find(Producto.class, id);
        } catch (Exception e) {
            System.out.println(" Error al buscar el producto: " + e.getMessage());
        }
        return producto;
    }

    @Override
    public List<Producto> obtenerTodos() {
        List<Producto> productos = null;
        try {
            TypedQuery<Producto> query = em.createQuery("SELECT p FROM Producto p WHERE p.estado = true", Producto.class);
            productos = query.getResultList();
        } catch (Exception e) {
            System.out.println(" Error al obtener la lista de productos: " + e.getMessage());
        }
        return productos;
    }

    @Override
    public void eliminarLogico(Long id) {
        try {
            em.getTransaction().begin();
            Producto producto = em.find(Producto.class, id);
            if (producto != null) {
                producto.setEstado(false);
                em.merge(producto);
                System.out.println("Producto eliminado lógicamente.");
            } else {
                System.out.println("No se encontró el producto con ID " + id);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Error al eliminar el producto: " + e.getMessage());
        }
    }
}
