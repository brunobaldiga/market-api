package dev.bruno.marketapi.common;

import dev.bruno.marketapi.entity.Payment;
import dev.bruno.marketapi.entity.Order;
import dev.bruno.marketapi.entity.dto.CreatePaymentDto;
import dev.bruno.marketapi.entity.dto.PaymentDto;
import dev.bruno.marketapi.type.PaymentMethod;

import java.math.BigDecimal;

public class PaymentConstants {

    public static CreatePaymentDto createCreatePaymentDto() {
        return new CreatePaymentDto(1L, PaymentMethod.CASH, null, BigDecimal.valueOf(2000));
    }

    public static PaymentDto createPaymentDto() {
        return new PaymentDto(1L, 1L, PaymentMethod.CASH, BigDecimal.valueOf(2000));
    }

    public static Payment createPayment(Order order) {
        return new Payment(1L, order, PaymentMethod.CASH, BigDecimal.valueOf(2000));
    }
}
