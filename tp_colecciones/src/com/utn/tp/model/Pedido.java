package com.utn.tp.model;

import com.utn.tp.enums.Estado;
import com.utn.tp.enums.FormaPago;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Pedido realizado por un Usuario.
 * Identidad de negocio: el ID (un pedido es único en sí mismo; no tiene
 * un campo de negocio natural que lo identifique de forma única, por eso
 * acá SÍ usamos id en equals/hashCode).
 *
 * Implementa Calculable -> calcularTotal() suma los subtotales del Set de detalles.
 * Relación con DetallePedido: composición 1..m (Set).
 */
public class Pedido extends Base implements Calculable {

    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;

    private Set<DetallePedido> detalles = new HashSet<>();

    public Pedido() {
        super();
        this.fecha = LocalDate.now();
        this.total = 0.0;
    }

    public Pedido(Long id, Estado estado, FormaPago formaPago) {
        super(id);
        this.fecha = LocalDate.now();
        this.estado = estado;
        this.formaPago = formaPago;
        this.total = 0.0;
    }

    /**
     * Agrega un detalle al pedido a partir de cantidad y producto.
     * Si ya existe un detalle para ese producto (equals por producto),
     * el Set no lo duplica.
     */
    public void addDetallePedido(int cantidad, Producto producto) {
        DetallePedido detalle = new DetallePedido(
                (long) (detalles.size() + 1), cantidad, producto);
        this.detalles.add(detalle);
        calcularTotal();
    }

    /**
     * Busca el detalle que contiene el producto indicado.
     */
    public DetallePedido findDetallePedidoByProducto(Producto producto) {
        for (DetallePedido d : detalles) {
            if (Objects.equals(d.getProducto(), producto)) {
                return d;
            }
        }
        return null;
    }

    /**
     * Elimina el detalle asociado al producto indicado.
     */
    public void deleteDetallePedidoByProducto(Producto producto) {
        DetallePedido detalle = findDetallePedidoByProducto(producto);
        if (detalle != null) {
            detalles.remove(detalle);
            calcularTotal();
        }
    }

    @Override
    public void calcularTotal() {
        double acumulado = 0.0;
        for (DetallePedido d : detalles) {
            acumulado += d.getSubtotal();
        }
        this.total = acumulado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public Set<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(Set<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido{")
                .append("id=").append(getId())
                .append(", fecha=").append(fecha)
                .append(", estado=").append(estado)
                .append(", formaPago=").append(formaPago)
                .append(", total=").append(total)
                .append(", detalles=[");
        boolean primero = true;
        for (DetallePedido d : detalles) {
            if (!primero) {
                sb.append(", ");
            }
            sb.append(d.toString());
            primero = false;
        }
        sb.append("]}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(getId(), pedido.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
