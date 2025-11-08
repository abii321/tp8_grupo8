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
        } finally {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
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
        } finally {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
        }
    }

    @Override
    public Factura obtenerFacturaPorId(Long idFactura) {
        try {
            return manager.find(Factura.class, idFactura);
        } catch (Exception e) {
            System.out.println("❌ Error al buscar factura por ID.");
            e.printStackTrace();
            return null;
        } finally {
            // No se cierra el EntityManager porque es estático
        }
    }

    @Override
    public List<Factura> obtenerFacturas() {
        try {
            TypedQuery<Factura> query = manager.createQuery("SELECT f FROM Factura f", Factura.class);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println("❌ Error al obtener las facturas.");
            e.printStackTrace();
            return null;
        } finally {
            // No cerrar el manager
        }
    }

    @Override
    public List<Factura> obtenerFacturasConMontoMayorA(double monto) {
        try {
            TypedQuery<Factura> query = manager.createQuery(
                "SELECT f FROM Factura f WHERE f.total > :monto", Factura.class);
            query.setParameter("monto", monto);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println("❌ Error al obtener facturas con monto mayor a " + monto);
            e.printStackTrace();
            return null;
        } finally {
            // No cerrar el manager
        }
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
            e.printStackTrace();
        } finally {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
        }
    }
}
