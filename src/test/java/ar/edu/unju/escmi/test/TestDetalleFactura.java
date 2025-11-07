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
        IProductoDao productoDao = new ProductoDaoImp();

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
        productoDao.guardarProducto(producto);

        // Crear factura
        Factura factura = new Factura();
        factura.setFecha(LocalDate.now());
        factura.setCliente(cliente);
        factura.setDomicilio(cliente.getDomicilio());
        factura.setTotal(0.0);
        factura.setEstado(true);
        facturaDao.guardarFactura(factura);

        // Crear detalle
        DetalleFactura detalle = new DetalleFactura();
        detalle.setProducto(producto);
        detalle.setFactura(factura);
        detalle.setCantidad(2);
        detalle.setSubtotal(producto.getPrecioUnitario() * detalle.getCantidad());

        detalleDao.guardarDetalle(detalle);

        assertNotNull(detalle.getId(), "El detalle debería tener un ID después de guardarse");

        List<DetalleFactura> detalles = detalleDao.obtenerDetalles();
        assertFalse(detalles.isEmpty(), "La lista de detalles no debería estar vacía");
        assertEquals(2, detalle.getCantidad(), "La cantidad debería ser 2");
        assertEquals(190000.0, detalle.getSubtotal(), "El subtotal debe ser cantidad * precioUnitario");
    }

    @Test
    void testBuscarDetallePorFactura() {
        IDetalleFacturaDao detalleDao = new DetalleFacturaDaoImp();
        IFacturaDao facturaDao = new FacturaDaoImp();
        IClienteDao clienteDao = new ClienteDaoImp();
        IProductoDao productoDao = new ProductoDaoImp();

        // Crear cliente
        Cliente cliente = new Cliente("Luna", "García", "Yala", 22113344, true);
        clienteDao.guardarCliente(cliente);

        // Crear producto
        Producto producto = new Producto("Teclado mecánico", 50000.0, true);
        productoDao.guardarProducto(producto);

        // Crear factura
        Factura factura = new Factura(LocalDate.now(), cliente, cliente.getDomicilio(), 0.0, true);
        facturaDao.guardarFactura(factura);

        // Crear detalle
        DetalleFactura detalle = new DetalleFactura();
        detalle.setFactura(factura);
        detalle.setProducto(producto);
        detalle.setCantidad(1);
        detalle.setSubtotal(producto.getPrecioUnitario() * detalle.getCantidad());
        detalleDao.guardarDetalle(detalle);

        // Buscar detalle por factura
        List<DetalleFactura> detallesFactura = detalleDao.obtenerDetallesPorFactura(factura.getId());
        assertNotNull(detallesFactura);
        assertFalse(detallesFactura.isEmpty(), "Debe haber detalles asociados a la factura");
        assertEquals(producto.getDescripcion(), detallesFactura.get(0).getProducto().getDescripcion(),
                "El producto del detalle debe coincidir con el guardado");
    }
}
