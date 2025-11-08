package ar.edu.unju.escmi.dao.imp;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IClienteDao;
import ar.edu.unju.escmi.entities.Cliente;

public class ClienteDaoImp implements IClienteDao {

    // Métodos de la interfaz
    @Override
    public void guardarCliente(Cliente cliente) {
        EntityManager em = EmfSingleton.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
     
            tx.begin();
            em.persist(cliente);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            if (em.isOpen()) em.close();
        }
    }

    @Override
    public void modificarCliente(Cliente cliente) {
        EntityManager em = EmfSingleton.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(cliente);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            if (em.isOpen()) em.close();
        }
    }

    @Override
    public List<Cliente> obtenerClientes() {
        EntityManager em = EmfSingleton.getEntityManager();
        List<Cliente> clientes = null;
        try {
            TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c", Cliente.class);
            clientes = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em.isOpen()) em.close();
        }
        return clientes;
    }

    @Override
    public Cliente buscarPorDni(String dni) {
        EntityManager em = EmfSingleton.getEntityManager();
        Cliente cliente = null;
        try {
            TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c WHERE c.dni = :dni AND c.estado = true", Cliente.class);
            query.setParameter("dni", dni);
            cliente = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("No se encontró un cliente con ese DNI.");
        } catch (Exception e) {
            System.out.println("Error al buscar el cliente: " + e.getMessage());
        } finally {
            em.close();
        }
        return cliente;
    }

}
