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

    public OrderItem() {

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

    public OrderItem(Product product, Integer quantity, BigDecimal priceAtPurchase) {
        this.product = product;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public BigDecimal getPriceAtPurchase() {
        return priceAtPurchase;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
