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

    private EntityManager manager;

    public ClienteDaoImp() {
        this.manager = EmfSingleton.getInstance().getEmf().createEntityManager();
    }

    @Override
    public void guardarCliente(Cliente cliente) {
        EntityTransaction tx = manager.getTransaction();
        try {
            tx.begin();
            manager.persist(cliente);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.out.println("No se pudo guardar el objeto cliente: " + e.getMessage());
        }
    }

    @Override
    public void modificarCliente(Cliente cliente) {
        EntityTransaction tx = manager.getTransaction();
        try {
            tx.begin();
            manager.merge(cliente);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.out.println("No se pudo modificar el objeto cliente: " + e.getMessage());
        }
    }

    @Override
    public void borrarCliente(Cliente cliente) {
        EntityTransaction tx = manager.getTransaction();
        try {
            tx.begin();
            manager.remove(manager.contains(cliente) ? cliente : manager.merge(cliente));
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.out.println("No se pudo eliminar el cliente: " + e.getMessage());
        }
    }

    @Override
    public List<Cliente> obtenerClientes() {
        TypedQuery<Cliente> query = manager.createQuery("SELECT c FROM Cliente c", Cliente.class);
        return query.getResultList();
    }

    @Override
public Cliente buscarPorDni(int dni) {
    Cliente cliente = null;
    try {
        TypedQuery<Cliente> query = manager.createQuery(
            "SELECT c FROM Cliente c WHERE c.dni = :dni", Cliente.class);
        query.setParameter("dni", dni);
        cliente = query.getSingleResult();
    } catch (NoResultException e) {
        System.out.println("No se encontró cliente con ese DNI");
    } catch (Exception e) {
        System.out.println("Error al buscar el cliente: " + e.getMessage());
    }
    return cliente;
}

    public void cerrar() {
        if (manager.isOpen()) manager.close();
    }
}
