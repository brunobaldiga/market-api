package dev.bruno.marketapi.unit;

import dev.bruno.marketapi.common.OrderConstants;
import dev.bruno.marketapi.common.PaymentConstants;
import dev.bruno.marketapi.entity.Order;
import dev.bruno.marketapi.entity.OrderItem;
import dev.bruno.marketapi.entity.Payment;
import dev.bruno.marketapi.entity.dto.OrderDto;
import dev.bruno.marketapi.entity.dto.PaymentDto;
import dev.bruno.marketapi.exception.EntityNotFoundException;
import dev.bruno.marketapi.exception.OrderAlreadyPaidException;
import dev.bruno.marketapi.repository.OrderRepository;
import dev.bruno.marketapi.repository.PaymentRepository;
import dev.bruno.marketapi.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTests {
    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderRepository orderRepository;

    @Test
    public void chargeOrder_WithValidPayment_ReturnsOrderDto() {
        OrderItem orderItem = OrderConstants.createOrderItem();
        Order order = OrderConstants.createOrder(false, List.of(orderItem));
        Payment payment = PaymentConstants.createPayment(order);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        when(paymentRepository.save(any(Payment.class))).thenReturn(
                payment
        );


        OrderDto orderDtoResult = paymentService.processPayment(PaymentConstants.createCreatePaymentDto());

        assertThat(orderDtoResult.id()).isEqualTo(order.getId());
        assertThat(orderDtoResult.createdAt()).isEqualTo(order.getCreatedAt());
        assertThat(orderDtoResult.change()).isEqualTo(order.getChangeAmount());
        assertThat(orderDtoResult.total()).isEqualTo(order.getTotal());

        assertThat(orderDtoResult.orderItems().get(0).productId()).isEqualTo(orderItem.getProduct().getId());
        assertThat(orderDtoResult.orderItems().get(0).productName()).isEqualTo(orderItem.getProduct().getName());
        assertThat(orderDtoResult.orderItems().get(0).quantity()).isEqualTo(orderItem.getQuantity());
        assertThat(orderDtoResult.orderItems().get(0).priceAtPurchase()).isEqualTo(orderItem.getPriceAtPurchase());
        assertThat(orderDtoResult.orderItems().get(0).total()).isEqualTo(
                orderItem.getPriceAtPurchase().multiply(BigDecimal.valueOf(orderItem.getQuantity()))
        );

        assertThat(orderDtoResult.payments().get(0).id()).isEqualTo(payment.getId());
        assertThat(orderDtoResult.payments().get(0).orderId()).isEqualTo(payment.getOrder().getId());
        assertThat(orderDtoResult.payments().get(0).paymentMethod()).isEqualTo(payment.getPaymentMethod());
        assertThat(orderDtoResult.payments().get(0).amountPaid()).isEqualTo(payment.getAmountPaid());
    }

    @Test
    public void chargePaidOrder_WithValidPayment_ReturnsOrderAlreadyPaidException() {
        OrderItem orderItem = OrderConstants.createOrderItem();
        Order paidOrder = OrderConstants.createOrder(true, List.of(orderItem));

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(paidOrder));

        assertThatThrownBy(() -> paymentService.processPayment(PaymentConstants.createCreatePaymentDto()))
                .isInstanceOf(OrderAlreadyPaidException.class);
    }

    @Test
    public void findPayment_ByExistingId_ReturnsPaymentDto() {
        OrderItem orderItem = OrderConstants.createOrderItem();
        Order order = OrderConstants.createOrder(false, List.of(orderItem));
        Payment payment = PaymentConstants.createPayment(order);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(payment));

        PaymentDto paymentDtoResult = paymentService.findPaymentById(payment.getId());

        assertThat(paymentDtoResult.id()).isEqualTo(payment.getId());
        assertThat(paymentDtoResult.orderId()).isEqualTo(payment.getOrder().getId());
        assertThat(paymentDtoResult.paymentMethod()).isEqualTo(payment.getPaymentMethod());
        assertThat(paymentDtoResult.amountPaid()).isEqualTo(payment.getAmountPaid());
    }

    @Test
    public void findPayment_ByInvalidId_ReturnsEntityNotFoundException() {
        when(paymentRepository.findById(anyLong())).thenThrow(EntityNotFoundException.class);

        assertThatThrownBy(() -> paymentService.findPaymentById(1L)).isInstanceOf(EntityNotFoundException.class);
    }
}
