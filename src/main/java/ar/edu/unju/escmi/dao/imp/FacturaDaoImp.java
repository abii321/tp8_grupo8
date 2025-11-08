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
            if (manager.getTransaction()!=null) manager.getTransaction().rollback();
            System.out.println("No se pudo guardar la factura.");
        }
        finally {
            manager.close();
        }
    }

    @Override
    public void borrarFactura(Factura factura) {
        EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();
        try {
            manager.getTransaction().begin();
            factura.setEstado(false); // eliminación lógica
            manager.merge(factura);
            manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction()!=null) manager.getTransaction().rollback();
            System.out.println("No se pudo eliminar la factura.");
        }
        finally {
            manager.close();
        }
    }

    @Override
    public Factura obtenerFacturaPorId(Long idFactura) {
        EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();
        return manager.find(Factura.class, idFactura);
    }

    @Override
    public List<Factura> obtenerFacturas() {
        EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();
        TypedQuery<Factura> query = manager.createQuery("SELECT f FROM Factura f", Factura.class);
        List<Factura> facturas = query.getResultList();
		return facturas;
    }

    @Override
    public List<Factura> obtenerFacturasConMontoMayorA(double monto) {
        EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();
        TypedQuery<Factura> query = manager.createQuery(
                "SELECT f FROM Factura f WHERE f.total > :monto", Factura.class);
        query.setParameter("monto", monto);
        return query.getResultList();
    }
}
