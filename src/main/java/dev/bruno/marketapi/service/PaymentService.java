package dev.bruno.marketapi.service;

import dev.bruno.marketapi.client.CardValidatorClient;
import dev.bruno.marketapi.client.dto.ValidationResponse;
import dev.bruno.marketapi.entity.Order;
import dev.bruno.marketapi.entity.Payment;
import dev.bruno.marketapi.entity.dto.CreatePaymentDto;
import dev.bruno.marketapi.entity.dto.OrderDto;
import dev.bruno.marketapi.entity.dto.PaymentDto;
import dev.bruno.marketapi.exception.EntityNotFoundException;
import dev.bruno.marketapi.exception.InvalidCardException;
import dev.bruno.marketapi.exception.OrderAlreadyPaidException;
import dev.bruno.marketapi.exception.PaymentMismatchException;
import dev.bruno.marketapi.repository.OrderRepository;
import dev.bruno.marketapi.repository.PaymentRepository;
import dev.bruno.marketapi.type.PaymentMethod;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final CardValidatorClient cardValidatorClient;

    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository, CardValidatorClient cardValidatorClient) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.cardValidatorClient = cardValidatorClient;
    }

    public PaymentDto findPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(id)
        );

        Order order = orderRepository.findById(payment.getOrder().getId()).orElseThrow(
                () -> new EntityNotFoundException(payment.getOrder().getId())
        );

        return payment.toDto(order.getTotal());
    }

    public OrderDto processPayment(CreatePaymentDto createPaymentDto) {
        Order order = orderRepository.findById(createPaymentDto.orderId()).orElseThrow(
                () -> new EntityNotFoundException(createPaymentDto.orderId())
        );

        if (order.getPaid()) throw new OrderAlreadyPaidException();

        if (createPaymentDto.paymentMethod().equals(PaymentMethod.DEBIT_CARD) || createPaymentDto.paymentMethod().equals(PaymentMethod.CREDIT_CARD)) {
            ValidationResponse response = cardValidatorClient.isValid().getBody();

            if (response == null || !Boolean.TRUE.equals(response.isSuccess())) {
                throw new InvalidCardException();
            }

            BigDecimal totalToBePaid = order.calculatePaymentsTotal().add(createPaymentDto.amountPaid());

            if (totalToBePaid.compareTo(order.getTotal()) > 0) {
                throw new PaymentMismatchException();
            }
        }

        Payment payment = createPaymentDto.toPayment();
        payment.setOrder(order);

        payment = paymentRepository.save(payment);

        order.getPayments().add(payment);

        BigDecimal paymentsTotal = order.calculatePaymentsTotal();

        if (paymentsTotal.compareTo(order.getTotal()) >= 0) order.setPaid(true);

        order.calculateChange();

        orderRepository.save(order);

        return order.toDto();
    }
}
