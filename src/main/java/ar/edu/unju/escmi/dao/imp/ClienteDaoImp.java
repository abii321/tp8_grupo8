package ar.edu.unju.escmi.dao.imp;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IClienteDao;
import ar.edu.unju.escmi.entities.Cliente;

public class ClienteDaoImp implements IClienteDao {

    private static EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();

    @Override
    public void guardarCliente(Cliente cliente) {
        try {
            manager.getTransaction().begin();
            manager.persist(cliente);
            manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction() != null) {
                manager.getTransaction().rollback();
            }
            System.out.println("No se pudo guardar el objeto cliente");
        } finally {
            manager.close();
        }
    }

    @Override
    public void modificarCliente(Cliente cliente) {
        try {
            manager.getTransaction().begin();
            manager.merge(cliente);
            manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction() != null) {
                manager.getTransaction().rollback();
            }
            System.out.println("No se pudo modificar el objeto cliente");
        } finally {
            manager.close();
        }
    }

    @Override
    public void borrarCliente(Cliente cliente) {
        try {
            manager.getTransaction().begin();
            manager.remove(manager.contains(cliente) ? cliente : manager.merge(cliente));
            manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction() != null) {
                manager.getTransaction().rollback();
            }
            System.out.println("No se pudo eliminar el cliente");
        } finally {
            manager.close();
        }
    }

    @Override
    public List<Cliente> obtenerClientes() {
        TypedQuery<Cliente> query = (TypedQuery<Cliente>) manager.createQuery("SELECT c FROM Cliente c", Cliente.class);
        List<Cliente> clientes = query.getResultList();
        return clientes;
    }

   @Override
public Cliente buscarPorDni(String dni) {
    try {
        manager.getTransaction().begin();
        TypedQuery<Cliente> query = manager.createQuery("SELECT c FROM Cliente c WHERE c.dni = :dni", Cliente.class);
        query.setParameter("dni", dni);
        Cliente cliente = query.getSingleResult();
        manager.getTransaction().commit();
        return cliente;
    } catch (NoResultException e) {
        System.out.println("No se encontró cliente con ese DNI");
        return null;
    } catch (Exception e) {
        if (manager.getTransaction() != null) {
            manager.getTransaction().rollback();
        }
        System.out.println("Error al buscar el cliente: " + e.getMessage());
        return null;
    } finally {
        manager.close();
    }
}
}
