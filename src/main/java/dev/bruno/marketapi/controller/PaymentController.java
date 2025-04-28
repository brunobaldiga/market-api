package dev.bruno.marketapi.controller;

import dev.bruno.marketapi.entity.dto.CreatePaymentDto;
import dev.bruno.marketapi.entity.dto.OrderDto;
import dev.bruno.marketapi.entity.dto.PaymentDto;
import dev.bruno.marketapi.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/payment")
@Tag(name = "Payment", description = "Endpoints for processing and retrieving payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get a payment by ID",
            description = "Retrieves the details of a specific payment using its unique ID."
    )
    public ResponseEntity<PaymentDto> getPayment(@PathVariable long id) {
        return ResponseEntity.ok(paymentService.findPaymentById(id));
    }

    @PostMapping("/charge")
    @Operation(
            summary = "Process a payment",
            description = "Processes a payment for an existing order. Returns the updated order information after successful payment."
    )
    public ResponseEntity<OrderDto> processPayment(@RequestBody @Valid CreatePaymentDto createPaymentDto) {
        OrderDto orderDto = paymentService.processPayment(createPaymentDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(orderDto.id())
                .toUri();

        return ResponseEntity.created(location).body(orderDto);
    }
}
