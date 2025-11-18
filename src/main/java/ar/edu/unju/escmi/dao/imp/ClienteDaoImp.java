package ar.edu.unju.escmi.dao.imp;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IClienteDao;
import ar.edu.unju.escmi.entities.Cliente;

public class ClienteDaoImp implements IClienteDao {

	@Override
	public void guardarCliente(Cliente cliente) {
        EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();
		try {
			manager.getTransaction().begin();
			manager.persist(cliente);
			manager.getTransaction().commit();
		}catch(Exception e) {
			if(manager.getTransaction() != null) {
				manager.getTransaction().rollback();
			}
			System.out.println("No se pudo guardar el objeto cliente");
		}finally {
			manager.close();
        }
	}

    @Override
    public void modificarCliente(Cliente cliente) {
        EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();
        try {
			manager.getTransaction().begin();
			manager.merge(cliente);
			manager.getTransaction().commit();
		}catch(Exception e) {
			if(manager.getTransaction() != null) {
				manager.getTransaction().rollback();
			}
			System.out.println("No se pudo modificar el cliente");
		}finally {
			manager.close();
        }
    }

    @Override
    public List<Cliente> obtenerClientes() {
        EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();
        TypedQuery<Cliente> query = (TypedQuery<Cliente>) manager.createQuery("select * from Autor a", Cliente.class);
		List<Cliente> clientes = query.getResultList();
        return clientes;
    }

    @Override
    public Cliente buscarPorDni(int dni) {
    	EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();
    	try {
        	TypedQuery<Cliente> query = manager.createQuery(
            	"SELECT c FROM Cliente c WHERE c.dni = :dni", Cliente.class
        	);
        	query.setParameter("dni", dni);

        	return query.getSingleResult();

    	} catch (Exception e) {
	        return null; // o lanzar excepción
	    } finally {
        	manager.close();
    	}
	}

}
