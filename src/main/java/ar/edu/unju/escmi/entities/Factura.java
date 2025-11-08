package ar.edu.unju.escmi.entities;

import java.time.LocalDate;
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

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleFactura> detalles;

    // Constructor vacío
    public Factura() {}

    // Constructor completo
    public Factura(LocalDate fecha, Cliente cliente, String domicilio, double total, boolean estado) {
        this.fecha = fecha;
        this.cliente = cliente;
        this.domicilio = domicilio;
        this.total = total;
        this.estado = estado;
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
    public void setDetalles(List<DetalleFactura> detalles) { this.detalles = detalles; }

    public double calcularTotal() {
        double total = 0.0;
        for (DetalleFactura detalle : detalles) {
            total += detalle.getSubtotal(); 
        }
        return total;
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
                '}';
    }

    public void agregarDetalle(Producto producto, int cantidad) {
        DetalleFactura detalle = new DetalleFactura(this, producto, cantidad);
        this.detalles.add(detalle);
    }
}
