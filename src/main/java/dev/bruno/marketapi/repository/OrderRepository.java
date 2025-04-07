package dev.bruno.marketapi.repository;

import dev.bruno.marketapi.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
