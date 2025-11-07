package ar.edu.unju.escmi.dao.imp;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IClienteDao;
import ar.edu.unju.escmi.entities.Cliente;
import java.util.Scanner;


public class ClienteDaoImp implements IClienteDao {

    private EntityManager em;

    public ClienteDaoImp() {
        this.em = EmfSingleton.getEntityManager();
    }

    @Override
    public void guardarCliente(Cliente cliente) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(cliente);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void modificarCliente(Cliente cliente) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(cliente);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Cliente> obtenerClientes() {
        TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c", Cliente.class);
        return query.getResultList();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        return em.find(Cliente.class, id);
    }

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
    em.getTransaction().begin();
    em.persist(cliente);
    em.getTransaction().commit();
    em.close();

    System.out.println("Cliente guardado correctamente.");
}

public void modificarCliente(Scanner sc) {
    System.out.print("Ingrese ID del cliente a modificar: ");
    Long id = Long.parseLong(sc.nextLine());

    EntityManager em = EmfSingleton.getEntityManager();
    Cliente cliente = em.find(Cliente.class, id);
    if (cliente == null) {
        System.out.println("Cliente no encontrado.");
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

    em.getTransaction().begin();
    em.merge(cliente);
    em.getTransaction().commit();
    em.close();

    System.out.println("Cliente actualizado.");
}

public void mostrarClientes() {
    EntityManager em = EmfSingleton.getEntityManager();
    List<Cliente> clientes = em.createQuery("FROM Cliente", Cliente.class).getResultList();
    clientes.forEach(System.out::println);
    em.close();
}


}

