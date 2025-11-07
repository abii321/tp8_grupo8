package ar.edu.unju.escmi.dao.imp;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IFacturaDao;
import ar.edu.unju.escmi.entities.Factura;

import java.util.Scanner;


public class FacturaDaoImp implements IFacturaDao {

    private EntityManager em;

    public FacturaDaoImp() {
        this.em = EmfSingleton.getEntityManager();
    }

    @Override
    public void guardarFactura(Factura factura) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(factura);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void borrarFactura(Factura factura) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Factura fact = em.find(Factura.class, factura.getId());
            if (fact != null) {
                em.remove(fact);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Factura obtenerFacturaPorId(Long idFactura) {
        return em.find(Factura.class, idFactura);
    }

    @Override
    public List<Factura> obtenerFacturas() {
        TypedQuery<Factura> query = em.createQuery("SELECT f FROM Factura f", Factura.class);
        return query.getResultList();
    }

    @Override
    public List<Factura> obtenerFacturasConMontoMayorA(double monto) {
        TypedQuery<Factura> query = em.createQuery(
            "SELECT f FROM Factura f WHERE f.total > :monto", Factura.class);
        query.setParameter("monto", monto);
        return query.getResultList();
    }

    public void altaFactura(Scanner sc) {
    Factura factura = new Factura();
    System.out.print("Ingrese monto: ");
    double total = factura.calcularTotal();
    System.out.println("Total de la factura: $" + total);

    EntityManager em = EmfSingleton.getEntityManager();
    em.getTransaction().begin();
    em.persist(factura);
    em.getTransaction().commit();
    em.close();

    System.out.println("Factura guardada.");
}

public void buscarFacturaPorId(Scanner sc) {
    System.out.print("Ingrese ID de factura: ");
    Long id = Long.parseLong(sc.nextLine());

    EntityManager em = EmfSingleton.getEntityManager();
    Factura factura = em.find(Factura.class, id);
    if (factura != null) {
        System.out.println(factura);
    } else {
        System.out.println("Factura no encontrada.");
    }
    em.close();
}

public void eliminarFactura(Scanner sc) {
    System.out.print("Ingrese ID de factura a eliminar: ");
    Long id = Long.parseLong(sc.nextLine());

    EntityManager em = EmfSingleton.getEntityManager();
    Factura factura = em.find(Factura.class, id);
    if (factura != null) {
        em.getTransaction().begin();
        em.remove(factura);
        em.getTransaction().commit();
        System.out.println("Factura eliminada.");
    } else {
        System.out.println("Factura no encontrada.");
    }
    em.close();
}

public void mostrarFacturas() {
    EntityManager em = EmfSingleton.getEntityManager();
    List<Factura> facturas = em.createQuery("FROM Factura", Factura.class).getResultList();
    facturas.forEach(System.out::println);
    em.close();
}

public void mostrarFacturasMayoresA(double monto) {
    EntityManager em = EmfSingleton.getEntityManager();
    List<Factura> facturas = em.createQuery(
        "FROM Factura f WHERE f.monto > :monto", Factura.class)
        .setParameter("monto", monto)
        .getResultList();
    facturas.forEach(System.out::println);
    em.close();
}

}
