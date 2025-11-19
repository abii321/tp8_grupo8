package ar.edu.unju.escmi.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.dao.*;
import ar.edu.unju.escmi.dao.imp.*;
import ar.edu.unju.escmi.entities.*;

class TestDetalleFactura {
    IDetalleFacturaDao detalleDao = new DetalleFacturaDaoImp();
    IFacturaDao facturaDao = new FacturaDaoImp();
    IClienteDao clienteDao = new ClienteDaoImp();
    IProductoDao productoDao = new ProductoDaoImp();

    @Test
    void testBuscarDetallePorFactura() {
        Cliente cliente = new Cliente("Luna", "García", "Yala", 22113344, true);
        clienteDao.guardarCliente(cliente);

        Producto producto = new Producto("Teclado mecánico", 50000.0, true);
        productoDao.guardarProducto(producto);

        Factura factura = new Factura(LocalDate.now(), cliente, cliente.getDomicilio(), 0.0, true);
        facturaDao.guardarFactura(factura);

        DetalleFactura detalle = new DetalleFactura();
        detalle.setFactura(factura);
        detalle.setProducto(producto);
        detalle.setCantidad(1);
        detalle.setSubtotal(producto.getPrecioUnitario());
        detalleDao.guardarDetalle(detalle);

        List<DetalleFactura> detallesFactura = detalleDao.obtenerDetallesPorFactura(factura.getId());
        DetalleFactura detalleBuscado = detallesFactura.get(detallesFactura.size() - 1); // último insertado

        assertNotNull(detallesFactura);
        assertFalse(detallesFactura.isEmpty(), "Debe haber detalles asociados a la factura");
        assertEquals(producto.getDescripcion(), detalleBuscado.getProducto().getDescripcion(),
                "El producto del detalle debe coincidir con el guardado");
    }
}
//---