package ar.edu.unju.escmi.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long id;

    @Column(name = "descripcion", nullable = false, length = 100)
    private String descripcion;

    @Column(name = "precio_unitario", nullable = false)
    private double precioUnitario;

    @Column(name = "estado", nullable = false)
    private boolean estado;

    // Constructor vacío (requerido por JPA)
    public Producto() {}

    // Constructor completo
    public Producto(String descripcion, double precioUnitario, boolean estado) {
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.estado = estado;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Producto{" +
               "id=" + id +
               ", descripcion='" + descripcion + '\'' +
               ", precioUnitario=" + precioUnitario +
               ", estado=" + estado +
               '}';
    }
}
