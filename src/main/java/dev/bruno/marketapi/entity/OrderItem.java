package dev.bruno.marketapi.entity;

import dev.bruno.marketapi.entity.dto.OrderItemDto;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
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

    public OrderItemDto toDto() {
        return new OrderItemDto(
                product.getId(),
                product.getName(),
                quantity,
                priceAtPurchase,
                priceAtPurchase.multiply(BigDecimal.valueOf(quantity))
        );
    }
}
