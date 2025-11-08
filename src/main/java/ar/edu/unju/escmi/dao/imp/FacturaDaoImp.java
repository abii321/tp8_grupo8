package ar.edu.unju.escmi.dao.imp;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IFacturaDao;
import ar.edu.unju.escmi.entities.Factura;

public class FacturaDaoImp implements IFacturaDao {

    @Override
    public void guardarFactura(Factura factura) {
        EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(factura);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.out.println("No se pudo guardar la factura.");
        } finally {
            em.close();
        }
    }

    @Override
    public void borrarFactura(Factura factura) {
        EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            factura.setEstado(false);
            em.merge(factura);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.out.println("No se pudo eliminar la factura.");
        } finally {
            em.close();
        }
    }

    @Override
    public Factura obtenerFacturaPorId(Long idFactura) {
        EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();
        Factura factura = em.find(Factura.class, idFactura);
        em.close();
        return factura;
    }

    @Override
    public List<Factura> obtenerFacturas() {
        EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();
        List<Factura> facturas = em.createQuery("SELECT f FROM Factura f", Factura.class).getResultList();
        em.close();
        return facturas;
    }

    @Override
    public List<Factura> obtenerFacturasConMontoMayorA(double monto) {
        EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();
        TypedQuery<Factura> query = em.createQuery("SELECT f FROM Factura f WHERE f.total > :monto", Factura.class);
        query.setParameter("monto", monto);
        List<Factura> facturas = query.getResultList();
        em.close();
        return facturas;
    }
}
