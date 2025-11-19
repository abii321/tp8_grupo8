package ar.edu.unju.escmi.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Long id;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "total", nullable = false)
    private double total;

    @Column(name = "domicilio")
    private String domicilio;

    @Column(name = "estado")
    private boolean estado;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<DetalleFactura> detalles;

    // Constructor vacío
    public Factura() {
        this.detalles = new ArrayList<>();
        this.total = 0.0;
        this.fecha = LocalDate.now();
    }

    // Constructor completo
    public Factura(LocalDate fecha, Cliente cliente, String domicilio, double total, boolean estado) {
        this.fecha = fecha;
        this.cliente = cliente;
        this.domicilio = domicilio;
        this.total = total;
        this.estado = estado;
        this.detalles = new ArrayList<>();
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getDomicilio() { return domicilio; }
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public List<DetalleFactura> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleFactura> detalles) {
        this.detalles = detalles;
        // asegurarse que cada detalle apunta a esta factura
        if (this.detalles != null) {
            this.detalles.forEach(d -> d.setFactura(this));
        }
    }

    /**
     * Crea un detalle y lo agrega a la factura (setea subtotal y referencia).
     * NO persiste aquí; la persistencia se realiza cuando se persiste la factura (cascade).
     */
    public void agregarDetalle(Producto producto, int cantidad) {
        DetalleFactura detalle = new DetalleFactura();
        detalle.setProducto(producto);
        detalle.setCantidad(cantidad);
        detalle.setSubtotal(producto.getPrecioUnitario() * cantidad);
        detalle.setFactura(this); // referencia necesaria para JPA
        this.detalles.add(detalle);
    }

    /**
     * Recalcula el total sumando subtotales de los detalles.
     */
    public double calcularTotal() {
        double totalCalculado = 0.0;
        if (this.detalles != null) {
            for (DetalleFactura detalle : detalles) {
                totalCalculado += detalle.getSubtotal();
            }
        }
        this.total = totalCalculado;
        return totalCalculado;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", total=" + total +
                ", domicilio='" + domicilio + '\'' +
                ", estado=" + estado +
                ", cliente=" + (cliente != null ? cliente.getNombre() : "null") +
                ", detalles=" + (detalles != null ? detalles.size() : 0) +
                '}';
    }
}