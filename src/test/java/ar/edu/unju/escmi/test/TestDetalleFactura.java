package ar.edu.unju.escmi.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.dao.*;
import ar.edu.unju.escmi.dao.imp.*;
import ar.edu.unju.escmi.entities.*;

class TestDetalleFactura {

    @Test
    void testGuardarYListarDetalles() {
        IDetalleFacturaDao detalleDao = new DetalleFacturaDaoImp();
        IFacturaDao facturaDao = new FacturaDaoImp();
        IClienteDao clienteDao = new ClienteDaoImp();

        // Crear cliente
        Cliente cliente = new Cliente();
        cliente.setNombre("Sofía");
        cliente.setApellido("Martínez");
        cliente.setDni(33444555);
        cliente.setDomicilio("Tilcara");
        cliente.setEstado(true);
        clienteDao.guardarCliente(cliente);

        // Crear producto
        Producto producto = new Producto();
        producto.setDescripcion("Monitor LED 24\"");
        producto.setPrecioUnitario(95000.0);
        producto.setEstado(true);

        // Crear factura
        Factura factura = new Factura();
        factura.setFecha(LocalDate.now());
        factura.setTotal(95000.0);
        factura.setCliente(cliente);
        facturaDao.guardarFactura(factura);

        // Crear detalle
        DetalleFactura detalle = new DetalleFactura();
        detalle.setCantidad(1);
        detalle.setSubtotal(95000.0);
        detalle.setFactura(factura);
        detalle.setProducto(producto);

        detalleDao.guardarDetalle(detalle);

        assertNotNull(detalle.getId(), "El detalle debería tener un ID después de guardarse");

        List<DetalleFactura> detalles = detalleDao.obtenerDetalles();
        assertFalse(detalles.isEmpty(), "La lista de detalles no debería estar vacía");
    }
}
