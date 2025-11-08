package ar.edu.unju.escmi.dao.imp;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IDetalleFacturaDao;
import ar.edu.unju.escmi.entities.DetalleFactura;

public class DetalleFacturaDaoImp implements IDetalleFacturaDao {

    @Override
    public void guardarDetalle(DetalleFactura detalle) {
        EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(detalle);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.out.println("No se pudo guardar el detalle de factura.");
        } finally {
            em.close();
        }
    }

    @Override
    public List<DetalleFactura> obtenerDetalles() {
        EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();
        List<DetalleFactura> detalles = em.createQuery("SELECT d FROM DetalleFactura d", DetalleFactura.class).getResultList();
        em.close();
        return detalles;
    }
}
