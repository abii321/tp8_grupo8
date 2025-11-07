package ar.edu.unju.escmi.dao.imp;

import java.util.List;
import java.util.Scanner;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IProductoDao;
import ar.edu.unju.escmi.entities.Producto;

public class ProductoDaoImp implements IProductoDao {

    private EntityManager em = EmfSingleton.getEntityManager();

    @Override
    public void guardarProducto(Producto producto) {
        try {
            em.getTransaction().begin();
            em.persist(producto);
            em.getTransaction().commit();
            System.out.println("Producto guardado correctamente.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Error al guardar el producto: " + e.getMessage());
        }
    }

    @Override
    public void borrarProducto(Producto producto) {
        try {
            em.getTransaction().begin();
            Producto prod = em.find(Producto.class, producto.getId());
            if (prod != null) {
                prod.setEstado(false);
                em.merge(prod);
                System.out.println("Producto eliminado lógicamente.");
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Error al borrar el producto: " + e.getMessage());
        }
    }

    @Override
    public double obtenerPrecioPorId(Long idProd) {
        double precio = 0;
        try {
            Producto producto = em.find(Producto.class, idProd);
            if (producto != null && producto.isEstado()) {
                precio = producto.getPrecioUnitario();
            }
        } catch (Exception e) {
            System.out.println("Error al obtener el precio: " + e.getMessage());
        }
        return precio;
    }

    @Override
    public void modificarPrecio(Long idProd, double nuevoPrecio) {
        try {
            em.getTransaction().begin();
            Producto producto = em.find(Producto.class, idProd);
            if (producto != null && producto.isEstado()) {
                producto.setPrecioUnitario(nuevoPrecio);
                em.merge(producto);
                System.out.println("Precio actualizado correctamente.");
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Error al modificar el precio: " + e.getMessage());
        }
    }

    // Métodos adicionales no definidos en la interfaz pero útiles

    public Producto buscarPorId(Long id) {
        Producto producto = null;
        try {
            producto = em.find(Producto.class, id);
        } catch (Exception e) {
            System.out.println("Error al buscar el producto: " + e.getMessage());
        }
        return producto;
    }

    public List<Producto> obtenerTodos() {
        List<Producto> productos = null;
        try {
            TypedQuery<Producto> query = em.createQuery("SELECT p FROM Producto p WHERE p.estado = true", Producto.class);
            productos = query.getResultList();
        } catch (Exception e) {
            System.out.println("Error al obtener la lista de productos: " + e.getMessage());
        }
        return productos;
    }

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

    public void altaProducto(Scanner sc) {
        Producto producto = new Producto();
        System.out.print("Ingrese nombre: ");
        producto.setNombre(sc.nextLine());
        System.out.print("Ingrese precio: ");
        producto.setPrecio(Double.parseDouble(sc.nextLine()));
        producto.setEstado(true);

        EntityManager em = EmfSingleton.getEntityManager();
        em.getTransaction().begin();
        em.persist(producto);
        em.getTransaction().commit();
        em.close();

        System.out.println("Producto registrado.");
    }

    public void modificarPrecio(Scanner sc) {
        System.out.print("Ingrese ID del producto: ");
        Long id = Long.parseLong(sc.nextLine());

        EntityManager em = EmfSingleton.getEntityManager();
        Producto producto = em.find(Producto.class, id);
        if (producto == null) {
            System.out.println("Producto no encontrado.");
            em.close();
            return;
        }

        System.out.print("Nuevo precio: ");
        producto.setPrecio(Double.parseDouble(sc.nextLine()));

        em.getTransaction().begin();
        em.merge(producto);
        em.getTransaction().commit();
        em.close();

        System.out.println("Precio actualizado.");
    }

    public void eliminarLogicoProducto(Scanner sc) {
        System.out.print("Ingrese ID del producto: ");
        Long id = Long.parseLong(sc.nextLine());

        EntityManager em = EmfSingleton.getEntityManager();
        Producto producto = em.find(Producto.class, id);
        if (producto != null) {
            producto.setEstado(false);
            em.getTransaction().begin();
            em.merge(producto);
            em.getTransaction().commit();
            System.out.println("Producto eliminado lógicamente.");
        } else {
            System.out.println("Producto no encontrado.");
        }
        em.close();
    }
}
