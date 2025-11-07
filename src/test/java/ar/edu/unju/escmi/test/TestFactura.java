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
    void testGuardarYBuscarFactura() {
        IFacturaDao facturaDao = new FacturaDaoImp();
        IClienteDao clienteDao = new ClienteDaoImp();

        Cliente cliente = new Cliente();
        cliente.setNombre("Juan");
        cliente.setApellido("Pérez");
        cliente.setDni(11222333);
        cliente.setDomicilio("San Salvador");
        cliente.setEstado(true);
        clienteDao.guardarCliente(cliente);

        Factura factura = new Factura();
        factura.setFecha(LocalDate.now());
        factura.setTotal(2500.50);
        factura.setCliente(cliente);
        factura.setEstado(true);

        facturaDao.guardarFactura(factura);

        assertNotNull(factura.getId(), "El ID de la factura no debería ser nulo después de guardar");

        Factura facturaBuscada = facturaDao.obtenerFacturaPorId(factura.getId());
        assertNotNull(facturaBuscada, "La factura guardada debería existir");
        assertEquals(cliente.getId(), facturaBuscada.getCliente().getId(), "El cliente asociado debe coincidir");
    }

    @Test
    void testObtenerFacturasConMontoMayorA() {
        IFacturaDao facturaDao = new FacturaDaoImp();
        IClienteDao clienteDao = new ClienteDaoImp();

        Cliente cliente = new Cliente("Laura", "Gómez", "Jujuy", 99887766, true);
        clienteDao.guardarCliente(cliente);

        Factura f1 = new Factura(LocalDate.now(), cliente, "Jujuy", 1000, true);
        Factura f2 = new Factura(LocalDate.now(), cliente, "Jujuy", 5000, true);

        facturaDao.guardarFactura(f1);
        facturaDao.guardarFactura(f2);

        List<Factura> facturas = facturaDao.obtenerFacturasConMontoMayorA(2000);
        assertTrue(facturas.stream().anyMatch(f -> f.getTotal() > 2000),
                "Debe haber facturas con monto mayor a 2000");
    }

    @Test
    void testBorrarFactura() {
        IFacturaDao facturaDao = new FacturaDaoImp();
        IClienteDao clienteDao = new ClienteDaoImp();

        Cliente cliente = new Cliente("Mario", "López", "Palpalá", 44332211, true);
        clienteDao.guardarCliente(cliente);

        Factura factura = new Factura(LocalDate.now(), cliente, "Palpalá", 3000.0, true);
        facturaDao.guardarFactura(factura);

        facturaDao.borrarFactura(factura);

        Factura facturaEliminada = facturaDao.obtenerFacturaPorId(factura.getId());
        assertFalse(facturaEliminada.isEstado(), "La factura debería estar marcada como inactiva");
    }
}
