package ar.edu.unju.escmi.dao;

import java.util.List;

import ar.edu.unju.escmi.entities.DetalleFactura;

public interface IDetalleFacturaDao {
    void guardarDetalle(DetalleFactura detalle);
    List<DetalleFactura> obtenerDetalles();
    List<DetalleFactura> obtenerDetallesPorFactura(Long idFactura);
}