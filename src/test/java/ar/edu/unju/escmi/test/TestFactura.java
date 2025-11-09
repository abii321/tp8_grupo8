package ar.edu.unju.escmi.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.dao.IClienteDao;
import ar.edu.unju.escmi.dao.IFacturaDao;
import ar.edu.unju.escmi.dao.imp.ClienteDaoImp;
import ar.edu.unju.escmi.dao.imp.FacturaDaoImp;
import ar.edu.unju.escmi.entities.Cliente;
import ar.edu.unju.escmi.entities.Factura;

class TestFactura {

    @Test
    void testBorrarFactura() {
        IFacturaDao facturaDao = new FacturaDaoImp();
        IClienteDao clienteDao = new ClienteDaoImp();

        Cliente cliente = new Cliente("Mario", "López", "Palpalá", 44332211, true);
        clienteDao.guardarCliente(cliente);

        Factura factura = new Factura(LocalDate.now(), cliente, "Palpalá", 3000.0, true);
        facturaDao.guardarFactura(factura);

        // 🔹 Buscar la factura con ID actualizado
        List<Factura> facturas = facturaDao.obtenerFacturas();
        Factura facturaGuardada = facturas.get(facturas.size() - 1);

        facturaDao.borrarFactura(facturaGuardada);

        Factura facturaEliminada = facturaDao.obtenerFacturaPorId(facturaGuardada.getId());
        assertFalse(facturaEliminada.isEstado(), "La factura debería estar marcada como inactiva");
    }

}
