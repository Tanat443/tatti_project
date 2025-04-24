package org.example.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.OrderRequestDTO;
import org.example.orderservice.dto.OrderResponseDTO;
import org.example.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Mono<ResponseEntity<OrderResponseDTO>> createOrder(@RequestBody OrderRequestDTO request) {
        return orderService.createOrder(request)
                .map(order -> ResponseEntity.status(HttpStatus.CREATED).body(order));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<OrderResponseDTO>> updateOrder(@PathVariable Long id, @RequestBody OrderRequestDTO request) {
        return orderService.updateOrder(id, request)
                .map(order -> ResponseEntity.ok(order))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<OrderResponseDTO>> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(order -> ResponseEntity.ok(order))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build()));
    }

    @GetMapping("/customer/{customerId}")
    public Flux<ResponseEntity<OrderResponseDTO>> getOrdersByCustomer(@PathVariable Long customerId) {
        return orderService.getOrdersByCustomerId(customerId)
                .map(order -> ResponseEntity.ok(order));
    }

    @GetMapping("/confectioner/{confectionerId}")
    public Flux<ResponseEntity<OrderResponseDTO>> getOrdersByConfectioner(@PathVariable Long confectionerId) {
        return orderService.getOrdersByConfectionerId(confectionerId)
                .map(order -> ResponseEntity.ok(order));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteOrder(@PathVariable Long id) {
        return orderService.deleteOrder(id)
                .then(Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).<Void>build()))  // Указание типа <Void>
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build()));
    }
}
