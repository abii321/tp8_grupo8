package ar.edu.unju.escmi.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "detalles_factura")
public class DetalleFactura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long id;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "subtotal", nullable = false)
    private double subtotal;

    // Relación con Factura
    @ManyToOne
    @JoinColumn(name = "id_factura", nullable = false)
    private Factura factura;

    // Relación con Producto
    @OneToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    public DetalleFactura(Factura factura, Producto producto, int cantidad) {
    this.factura = factura;
    this.producto = producto;
    this.cantidad = cantidad;
    }

    public DetalleFactura() {
        // Constructor vacío necesario para pruebas y JPA
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public Factura getFactura() { return factura; }
    public void setFactura(Factura factura) { this.factura = factura; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    @Override
    public String toString() {
        return "DetalleFactura{" +
                "id=" + id +
                ", cantidad=" + cantidad +
                ", subtotal=" + subtotal +
                ", producto=" + (producto != null ? producto.getDescripcion() : "null") +
                '}';
    }
}
