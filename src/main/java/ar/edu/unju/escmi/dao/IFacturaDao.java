package ar.edu.unju.escmi.dao;

import java.util.List;

import ar.edu.unju.escmi.entities.Factura;

public interface IFacturaDao {
    void guardarFactura(Factura factura);
    void borrarFactura(Factura factura);
    Factura obtenerFacturaPorId(Long idFactura);
    List<Factura> obtenerFacturas();
    List<Factura> obtenerFacturasConMontoMayorA(double monto);
}