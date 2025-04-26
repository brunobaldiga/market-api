package dev.bruno.marketapi.repository;

import dev.bruno.marketapi.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
