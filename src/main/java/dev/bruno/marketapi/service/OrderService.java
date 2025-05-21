package dev.bruno.marketapi.service;

import dev.bruno.marketapi.entity.Order;
import dev.bruno.marketapi.entity.OrderItem;
import dev.bruno.marketapi.entity.Product;
import dev.bruno.marketapi.entity.dto.CreateOrderDto;
import dev.bruno.marketapi.entity.dto.CreateOrderItemDto;
import dev.bruno.marketapi.entity.dto.OrderDto;
import dev.bruno.marketapi.exception.EntityNotFoundException;
import dev.bruno.marketapi.exception.InsufficientStockException;
import dev.bruno.marketapi.repository.OrderRepository;
import dev.bruno.marketapi.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderDto createOrder(CreateOrderDto orderDto) {
        List<OrderItem> orderItems = new ArrayList<>();

        for (CreateOrderItemDto orderItemDto : orderDto.orderItems()) {
            Product product = productRepository.findById(orderItemDto.productId()).orElseThrow(
                    () -> new EntityNotFoundException(orderItemDto.productId())
            );

            if (product.getQuantity() <= 0 || product.getQuantity() < orderItemDto.quantity()) {
                throw new InsufficientStockException(product.getName());
            }

            product.setQuantity(product.getQuantity() - orderItemDto.quantity());
            productRepository.save(product);

            OrderItem item = new OrderItem(
                    product,
                    orderItemDto.quantity(),
                    product.getPrice()
            );

            orderItems.add(item);
        }

        Order order = new Order(LocalDateTime.now(), orderItems);
        orderItems.forEach(item -> {
            item.setOrder(order);
        });


        order.calculateTotal();

        Order savedOrder = orderRepository.save(order);
        return savedOrder.toDto();
    }

    public OrderDto findOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(id)
        );

        return order.toDto();
    }

    public void deleteOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(id)
        );

        orderRepository.delete(order);

    }
}
