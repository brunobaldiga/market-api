package dev.bruno.marketapi.controller;

import dev.bruno.marketapi.entity.dto.CreatePaymentDto;
import dev.bruno.marketapi.entity.dto.OrderDto;
import dev.bruno.marketapi.entity.dto.PaymentDto;
import dev.bruno.marketapi.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPayment(@PathVariable long id) {
        return ResponseEntity.ok(paymentService.findPaymentById(id));
    }

    @PostMapping("/charge")
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
