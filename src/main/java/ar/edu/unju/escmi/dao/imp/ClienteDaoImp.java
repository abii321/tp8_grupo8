package ar.edu.unju.escmi.dao.imp;

import java.util.List;
import java.util.Scanner;
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

    // Métodos extra interactivos
    public void altaCliente(Scanner sc) {
        Cliente cliente = new Cliente();
        System.out.print("Ingrese nombre: ");
        cliente.setNombre(sc.nextLine());
        System.out.print("Ingrese apellido: ");
        cliente.setApellido(sc.nextLine());
        System.out.print("Ingrese domicilio: ");
        cliente.setDomicilio(sc.nextLine());
        System.out.print("Ingrese DNI: ");
        cliente.setDni(Integer.parseInt(sc.nextLine()));
        cliente.setEstado(true);

        EntityManager em = EmfSingleton.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(cliente);
            tx.commit();
            System.out.println("✅ Cliente guardado correctamente.");
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.out.println("❌ Error al guardar cliente.");
            e.printStackTrace();
        } finally {
            if (em.isOpen()) em.close();
        }
    }

    public void modificarCliente(Scanner sc) {
        System.out.print("Ingrese ID del cliente a modificar: ");
        Long id = Long.parseLong(sc.nextLine());

        EntityManager em = EmfSingleton.getEntityManager();
        Cliente cliente = em.find(Cliente.class, id);

        if (cliente == null) {
            System.out.println("⚠️ Cliente no encontrado.");
            em.close();
            return;
        }

        System.out.print("Nuevo nombre: ");
        cliente.setNombre(sc.nextLine());
        System.out.print("Nuevo apellido: ");
        cliente.setApellido(sc.nextLine());
        System.out.print("Nuevo domicilio: ");
        cliente.setDomicilio(sc.nextLine());
        System.out.print("Nuevo DNI: ");
        cliente.setDni(Integer.parseInt(sc.nextLine()));

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(cliente);
            tx.commit();
            System.out.println("✅ Cliente actualizado.");
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.out.println("❌ Error al modificar cliente.");
            e.printStackTrace();
        } finally {
            if (em.isOpen()) em.close();
        }
    }

    public void mostrarClientes() {
        EntityManager em = EmfSingleton.getEntityManager();
        List<Cliente> clientes = em.createQuery("FROM Cliente", Cliente.class).getResultList();
        clientes.forEach(System.out::println);
        em.close();
    }

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
