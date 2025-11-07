package ar.edu.unju.escmi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Clase que representa la entidad Producto
 * Mapeada con JPA para persistencia en base de datos
 */
@Entity
@Table(name = "productos")
public class Producto {

    // 🔹 Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;
    private double precioUnitario;
    private boolean estado;

    // 🔹 Constructores
    public Producto() {
    }

    public Producto(String descripcion, double precioUnitario, boolean estado) {
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.estado = estado;
    }

    // 🔹 Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setNombre(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }


    // 🔹 Métodos útiles
    @Override
    public String toString() {
        return "Producto [id=" + id + 
               ", descripcion=" + descripcion + 
               ", precioUnitario=" + precioUnitario + 
               ", estado=" + estado + "]";
    }
}
