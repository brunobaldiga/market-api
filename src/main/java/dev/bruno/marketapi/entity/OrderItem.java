package dev.bruno.marketapi.entity;

import dev.bruno.marketapi.entity.dto.OrderItemDto;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    private Integer quantity;

    private BigDecimal priceAtPurchase;

    @ManyToOne
    private Order order;

    public OrderItem() {}

    public OrderItem(Product product, Integer quantity, BigDecimal priceAtPurchase) {
        this.product = product;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }

    public OrderItem(Long id, Product product, Integer quantity, BigDecimal priceAtPurchase, Order order) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
        this.order = order;
    }

    public OrderItemDto toDto() {
        return new OrderItemDto(
                product.getId(),
                product.getName(),
                quantity,
                priceAtPurchase,
                priceAtPurchase.multiply(BigDecimal.valueOf(quantity))
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPriceAtPurchase() {
        return priceAtPurchase;
    }

    public void setPriceAtPurchase(BigDecimal priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
