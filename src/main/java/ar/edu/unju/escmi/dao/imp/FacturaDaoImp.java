package ar.edu.unju.escmi.dao.imp;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IFacturaDao;
import ar.edu.unju.escmi.entities.Factura;

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
        } finally {
            em.close();
        }
    }

    @Override
    public void borrarFactura(Factura factura) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            factura.setEstado(false);
            em.merge(factura);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public Factura obtenerFacturaPorId(Long idFactura) {
        try {
            return em.find(Factura.class, idFactura);
        } finally {
            em.close();
        }
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

    // Métodos auxiliares usados por el menú
    public void mostrarFacturas() {
        obtenerFacturas().forEach(System.out::println);
    }

    public void mostrarFacturasMayoresA(double monto) {
        obtenerFacturasConMontoMayorA(monto).forEach(System.out::println);
    }

}
