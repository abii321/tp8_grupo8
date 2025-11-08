package ar.edu.unju.escmi.dao.imp;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IDetalleFacturaDao;
import ar.edu.unju.escmi.entities.DetalleFactura;

public class DetalleFacturaDaoImp implements IDetalleFacturaDao {

    private EntityManager em;

    public DetalleFacturaDaoImp() {
        this.em = EmfSingleton.getEntityManager();
    }

    @Override
    public void guardarDetalle(DetalleFactura detalle) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(detalle);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public List<DetalleFactura> obtenerDetalles() {
        TypedQuery<DetalleFactura> query =
            em.createQuery("SELECT d FROM DetalleFactura d", DetalleFactura.class);
        return query.getResultList();
    }

    @Override
    public List<DetalleFactura> obtenerDetallesPorFactura(Long idFactura) {
        TypedQuery<DetalleFactura> query = em.createQuery(
            "SELECT d FROM DetalleFactura d WHERE d.factura.id = :idFactura", DetalleFactura.class);
        query.setParameter("idFactura", idFactura);
        return query.getResultList();
    }

}
