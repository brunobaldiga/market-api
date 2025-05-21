package dev.bruno.marketapi.controller;

import dev.bruno.marketapi.entity.Order;
import dev.bruno.marketapi.entity.dto.CreateOrderDto;
import dev.bruno.marketapi.entity.dto.OrderDto;
import dev.bruno.marketapi.entity.dto.ProductDto;
import dev.bruno.marketapi.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/order")
@Tag(name = "Order", description = "Endpoints for creating and retrieving orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create-order")
    @Operation(
            summary = "Create a new order",
            description = "Creates a new order with the provided products and customer details. Returns the created order information."
    )
    public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid CreateOrderDto createOrderDto) {
        OrderDto orderDto = orderService.createOrder(createOrderDto);


        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(orderDto.id())
                .toUri();

        return ResponseEntity.created(location).body(orderDto);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get an order by ID",
            description = "Retrieves the details of an existing order by its unique ID."
    )
    public ResponseEntity<OrderDto> getOrder(@PathVariable long id) {
        return ResponseEntity.ok(orderService.findOrderById(id));
    }

    @DeleteMapping("/delete-order/{id}")
    @Operation(
            summary = "Delete an order by ID",
            description = "Deletes an order from database using its unique ID."
    )
    public ResponseEntity<Void> deleteOrderById(@PathVariable Long id) {
        orderService.deleteOrderById(id);

        return ResponseEntity.noContent().build();
    }
}
