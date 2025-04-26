package dev.bruno.marketapi.entity;

import dev.bruno.marketapi.entity.dto.OrderDto;
import dev.bruno.marketapi.entity.dto.OrderItemDto;
import dev.bruno.marketapi.entity.dto.PaymentDto;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    private BigDecimal total = BigDecimal.ZERO;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

    private Boolean isPaid = false;

    @Column(name = "change_amount")
    private BigDecimal changeAmount = BigDecimal.ZERO;

    public Order() {}

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public OrderDto toDto() {

        List<OrderItemDto> orderItems = this.orderItems.stream()
                .map(OrderItem::toDto)
                .toList();

        List<PaymentDto> payments = this.payments.stream()
                .map(payment -> payment.toDto(this.total))
                .toList();

        return new OrderDto(id, createdAt, getChangeAmount(), getTotal(), orderItems, payments);
    }

    public BigDecimal calculateTotal() {
        this.orderItems.forEach(item -> {
            this.setTotal(
                    item.getPriceAtPurchase().multiply(
                            BigDecimal.valueOf(item.getQuantity())
                    )
            );
        });

        return getTotal();
    }

    public void calculateChange() {
        BigDecimal change = calculatePaymentsTotal().subtract(calculateTotal());
        setChangeAmount(change.signum() < 0 ? BigDecimal.ZERO : change);
    }

    public BigDecimal calculatePaymentsTotal() {
        return this.getPayments().stream().map(
                Payment::getAmountPaid
        ).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Order(LocalDateTime createdAt, List<OrderItem> orderItems) {
        this.createdAt = createdAt;
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public BigDecimal getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(BigDecimal changeAmount) {
        this.changeAmount = changeAmount;
    }
}
