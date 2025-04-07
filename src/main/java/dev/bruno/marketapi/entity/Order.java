package dev.bruno.marketapi.entity;

import dev.bruno.marketapi.entity.dto.OrderDto;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    private BigDecimal total;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public OrderDto toDto() {
        List<OrderItem> orderItems = this.orderItems.stream()
                .map(OrderItem::toDto)
                .toList();

        return new OrderDto(createdAt, total, orderItems);
    }
}
