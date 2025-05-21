package dev.bruno.marketapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.bruno.marketapi.entity.dto.PaymentDto;
import dev.bruno.marketapi.type.PaymentMethod;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @JsonIgnore
    private String cardInformation;

    private BigDecimal amountPaid;

    public Payment() {
    }

    public Payment(PaymentMethod paymentMethod, String cardInformation, BigDecimal amountPaid) {
        this.paymentMethod = paymentMethod;
        this.cardInformation = cardInformation;
        this.amountPaid = amountPaid;
    }

    public Payment(Long id, Order order, PaymentMethod paymentMethod, BigDecimal amountPaid) {
        this.id = id;
        this.order = order;
        this.paymentMethod = paymentMethod;
        this.amountPaid = amountPaid;
    }

    public PaymentDto toDto(BigDecimal total) {
        Long orderId = order != null ? order.getId() : null;

        return new PaymentDto(
                id,
                orderId,
                paymentMethod,
                amountPaid
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCardInformation() {
        return cardInformation;
    }

    public void setCardInformation(String cardInformation) {
        this.cardInformation = cardInformation;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }
}
