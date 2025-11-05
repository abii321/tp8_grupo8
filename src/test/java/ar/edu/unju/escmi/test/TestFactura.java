package ar.edu.unju.escmi.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.dao.IFacturaDao;
import ar.edu.unju.escmi.dao.IClienteDao;
import ar.edu.unju.escmi.dao.imp.FacturaDaoImp;
import ar.edu.unju.escmi.dao.imp.ClienteDaoImp;
import ar.edu.unju.escmi.entities.Cliente;
import ar.edu.unju.escmi.entities.Factura;

class TestFactura {

    @Test
    void testGuardarYBuscarFactura() {
        IFacturaDao facturaDao = new FacturaDaoImp();
        IClienteDao clienteDao = new ClienteDaoImp();

        // Creamos cliente para asociar
        Cliente cliente = new Cliente();
        cliente.setNombre("Juan");
        cliente.setApellido("Pérez");
        cliente.setDni(11222333);
        cliente.setDomicilio("San Salvador");
        cliente.setEstado(true);

        clienteDao.guardarCliente(cliente);

        // Creamos factura
        Factura factura = new Factura();
        factura.setFecha(LocalDate.now());
        factura.setTotal(2500.50);
        factura.setCliente(cliente);

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

        // Cliente base
        Cliente cliente = new Cliente();
        cliente.setNombre("Laura");
        cliente.setApellido("Gómez");
        cliente.setDni(99887766);
        cliente.setDomicilio("Jujuy");
        cliente.setEstado(true);
        clienteDao.guardarCliente(cliente);

        // Facturas de prueba
        Factura f1 = new Factura();
        f1.setFecha(LocalDate.now());
        f1.setTotal(1000);
        f1.setCliente(cliente);

        Factura f2 = new Factura();
        f2.setFecha(LocalDate.now());
        f2.setTotal(5000);
        f2.setCliente(cliente);

        facturaDao.guardarFactura(f1);
        facturaDao.guardarFactura(f2);

        List<Factura> facturas = facturaDao.obtenerFacturasConMontoMayorA(2000);
        assertTrue(facturas.stream().anyMatch(f -> f.getTotal() > 2000),
                "Debe haber facturas con monto mayor a 2000");
    }
}
