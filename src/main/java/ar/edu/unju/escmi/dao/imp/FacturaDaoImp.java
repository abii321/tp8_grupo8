package ar.edu.unju.escmi.dao.imp;

import java.util.List;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IFacturaDao;
import ar.edu.unju.escmi.entities.Factura;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class FacturaDaoImp implements IFacturaDao {

    private static EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();

    @Override
    public void guardarFactura(Factura factura) {
        try {
            manager.getTransaction().begin();
            manager.persist(factura);
            manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) manager.getTransaction().rollback();
            System.out.println("❌ No se pudo guardar la factura.");
            e.printStackTrace();
        }
    }

    @Override
    public void borrarFactura(Factura factura) {
        try {
            manager.getTransaction().begin();
            factura.setEstado(false); // eliminación lógica
            manager.merge(factura);
            manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) manager.getTransaction().rollback();
            System.out.println("❌ No se pudo eliminar la factura.");
            e.printStackTrace();
        }
    }

    @Override
    public Factura obtenerFacturaPorId(Long idFactura) {
        return manager.find(Factura.class, idFactura);
    }

    @Override
    public List<Factura> obtenerFacturas() {
        TypedQuery<Factura> query = manager.createQuery("SELECT f FROM Factura f", Factura.class);
        return query.getResultList();
    }

    @Override
    public List<Factura> obtenerFacturasConMontoMayorA(double monto) {
        TypedQuery<Factura> query = manager.createQuery(
                "SELECT f FROM Factura f WHERE f.total > :monto", Factura.class);
        query.setParameter("monto", monto);
        return query.getResultList();
    }

    @Override
    public void modificarFactura(Factura factura) {
        try {
            manager.getTransaction().begin();
            manager.merge(factura);
            manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) manager.getTransaction().rollback();
            System.out.println("❌ No se pudo modificar la factura.");
        }
    }
}
