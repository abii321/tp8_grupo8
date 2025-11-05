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
        this.em = EmfSingleton.getInstance().getEmf().createEntityManager();
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
}
