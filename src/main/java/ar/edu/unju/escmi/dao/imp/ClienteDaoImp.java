package ar.edu.unju.escmi.dao.imp;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IClienteDao;
import ar.edu.unju.escmi.entities.Cliente;

public class ClienteDaoImp implements IClienteDao {

    @Override
    public void guardarCliente(Cliente cliente) {
        EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(cliente);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.out.println("No se pudo guardar el cliente");
        } finally {
            em.close();
        }
    }

    @Override
    public void modificarCliente(Cliente cliente) {
        EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(cliente);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.out.println("No se pudo modificar el cliente");
        } finally {
            em.close();
        }
    }

    @Override
    public List<Cliente> obtenerClientes() {
        EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();
        List<Cliente> clientes = em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
        em.close();
        return clientes;
    }

    @Override

	public Cliente buscarPorDni(int dni) {
    EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();
		try {
			return em.createQuery("SELECT c FROM Cliente c WHERE c.dni = :dni", Cliente.class)
					.setParameter("dni", dni)
					.getSingleResult();
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}

}
