package ar.edu.unju.escmi.dao.imp;

import java.util.List;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IDetalleFacturaDao;
import ar.edu.unju.escmi.entities.DetalleFactura;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class DetalleFacturaDaoImp implements IDetalleFacturaDao {

    private static EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();

    @Override
    public void guardarDetalle(DetalleFactura detalle) {
        try {
            manager.getTransaction().begin();
            manager.persist(detalle);
            manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) manager.getTransaction().rollback();
            System.out.println("❌ No se pudo guardar el detalle de factura.");
            e.printStackTrace();
        }
    }

    @Override
    public List<DetalleFactura> obtenerDetalles() {
        TypedQuery<DetalleFactura> query = manager.createQuery(
                "SELECT d FROM DetalleFactura d", DetalleFactura.class);
        return query.getResultList();
    }

    @Override
    public List<DetalleFactura> obtenerDetallesPorFactura(Long idFactura) {
        TypedQuery<DetalleFactura> query = manager.createQuery(
                "SELECT d FROM DetalleFactura d WHERE d.factura.id = :idFactura", DetalleFactura.class);
        query.setParameter("idFactura", idFactura);
        return query.getResultList();
    }
}
