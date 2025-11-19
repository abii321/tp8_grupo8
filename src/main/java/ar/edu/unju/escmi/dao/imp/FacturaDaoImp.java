package ar.edu.unju.escmi.dao.imp;

import java.util.List;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IFacturaDao;
import ar.edu.unju.escmi.entities.Factura;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class FacturaDaoImp implements IFacturaDao {

    @Override
    public void guardarFactura(Factura factura) {
        EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();

        try {
            manager.getTransaction().begin();
            manager.persist(factura);
            manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            System.out.println("No se pudo guardar la factura.");
        } finally {
            manager.close();
        }
    }

    @Override
    public void borrarFactura(Factura factura) {
        EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();

        try {
            manager.getTransaction().begin();
            factura.setEstado(false);
            manager.merge(factura);
            manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            System.out.println("No se pudo eliminar la factura.");
        } finally {
            manager.close();
        }
    }

    @Override
    public Factura obtenerFacturaPorId(Long idFactura) {
        EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();

        try {
            return manager.find(Factura.class, idFactura);
        } finally {
            manager.close();
        }
    }

    @Override
    public List<Factura> obtenerFacturas() {
        EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();

        try {
            TypedQuery<Factura> query = manager.createQuery("SELECT f FROM Factura f", Factura.class);
            return query.getResultList();
        } finally {
            manager.close();
        }
    }

    @Override
    public List<Factura> obtenerFacturasConMontoMayorA(double monto) {
        EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();

        try {
            TypedQuery<Factura> query = manager.createQuery(
                    "SELECT f FROM Factura f WHERE f.total > :monto", 
                    Factura.class);
            query.setParameter("monto", monto);
            return query.getResultList();
        } finally {
            manager.close();
        }
    }
}
