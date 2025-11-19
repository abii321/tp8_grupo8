package ar.edu.unju.escmi.dao.imp;

import java.util.List;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IDetalleFacturaDao;
import ar.edu.unju.escmi.entities.DetalleFactura;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class DetalleFacturaDaoImp implements IDetalleFacturaDao {

    @Override
    public void guardarDetalle(DetalleFactura detalle) {
        EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();

        try {
            manager.getTransaction().begin();
            manager.persist(detalle);
            manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            System.out.println("No se pudo guardar el detalle de la factura.");
        } finally {
            manager.close();
        }
    }

    @Override
    public List<DetalleFactura> obtenerDetalles() {
        EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();

      try {
            TypedQuery<DetalleFactura> query =
                manager.createQuery("SELECT d FROM DetalleFactura d", DetalleFactura.class);
            return query.getResultList();
        } finally {
            manager.close();
        }
    }

    @Override
    public List<DetalleFactura> obtenerDetallesPorFactura(Long idFactura) {
        EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();

        try {
            TypedQuery<DetalleFactura> query = manager.createQuery(
                    "SELECT d FROM DetalleFactura d WHERE d.factura.id = :idFactura",
                    DetalleFactura.class);
            query.setParameter("idFactura", idFactura);
            return query.getResultList();
        } finally {
            manager.close();
        }
    }
}
