package ar.edu.unju.escmi.dao.imp;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IClienteDao;
import ar.edu.unju.escmi.entities.Cliente;

public class ClienteDaoImp implements IClienteDao {
    private static EntityManager manager =  EmfSingleton.getInstance().getEmf().createEntityManager();

	@Override
	public void guardarCliente(Cliente cliente) {
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
        TypedQuery<Cliente> query = (TypedQuery<Cliente>) manager.createQuery("select * from Autor a", Cliente.class);
		List<Cliente> clientes = query.getResultList();
        return clientes;
    }

    @Override
    public Cliente buscarPorDni(int dni) {
        return manager.find(Cliente.class, dni);
    }

}
