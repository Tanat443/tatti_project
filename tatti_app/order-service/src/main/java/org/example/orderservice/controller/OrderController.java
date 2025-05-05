package org.example.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.OrderRequestDTO;
import org.example.orderservice.dto.OrderResponseDTO;
import org.example.orderservice.dto.OrderWithNotificationDTO;
import org.example.orderservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@Tag(name = "Product API", description = "API для Управление Заказам")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Mono<ResponseEntity<OrderWithNotificationDTO>> createOrder(@RequestBody OrderRequestDTO dto) {
        return orderService.createOrderWithNotification(dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<OrderResponseDTO>> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }


    @PutMapping("/{id}")
    public Mono<ResponseEntity<OrderWithNotificationDTO>> updateOrder(@PathVariable Long id,
                                                                      @RequestBody OrderRequestDTO request) {
        return orderService.updateOrderWithNotification(id, request)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<OrderWithNotificationDTO>> deleteOrder(@PathVariable Long id) {
        return orderService.deleteOrderWithNotification(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @PatchMapping("/{id}/status")
    public Mono<ResponseEntity<OrderWithNotificationDTO>> changeStatus(
            @PathVariable Long id,
            @RequestBody OrderWithNotificationDTO statusDto) {
        return orderService.changeStatusWithNotification(id, statusDto.getStatus())
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().build()))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping("/customer/{customerId}")
    public Flux<ResponseEntity<OrderResponseDTO>> getOrdersByCustomer(@PathVariable Long customerId) {
        return orderService.getOrdersByCustomerId(customerId)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/confectioner/{confectionerId}")
    public Flux<ResponseEntity<OrderResponseDTO>> getOrdersByConfectioner(@PathVariable Long confectionerId) {
        return orderService.getOrdersByConfectionerId(confectionerId)
                .map(ResponseEntity::ok);
    }
}