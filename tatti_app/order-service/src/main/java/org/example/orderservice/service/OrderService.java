package org.example.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.OrderRequestDTO;
import org.example.orderservice.dto.OrderResponseDTO;
import org.example.orderservice.mapper.OrderMapper;
import org.example.orderservice.model.Order;
import org.example.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public Mono<OrderResponseDTO> createOrder(OrderRequestDTO request) {
        Order order = orderMapper.toEntity(request);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order)
                .map(orderMapper::toDto);
    }

    public Mono<OrderResponseDTO> updateOrder(Long id, OrderRequestDTO updatedDto) {
        return orderRepository.findById(id)
                .flatMap(existing -> {
                    Order updated = orderMapper.toEntity(updatedDto);
                    existing.setOrderType(updated.getOrderType());
                    existing.setDescription(updated.getDescription());
                    existing.setPrice(updated.getPrice());
                    existing.setStatus(updated.getStatus());
                    existing.setAssignedConfectionerId(updated.getAssignedConfectionerId());
                    existing.setLocation(updated.getLocation());
                    existing.setPushNotificationDeadline(updated.getPushNotificationDeadline());
                    existing.setUpdatedAt(LocalDateTime.now());
                    return orderRepository.save(existing);
                })
                .map(orderMapper::toDto);
    }

    public Mono<OrderResponseDTO> getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDto);
    }

    public Flux<OrderResponseDTO> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findAllByCustomerId(customerId)
                .map(orderMapper::toDto);
    }

    public Flux<OrderResponseDTO> getOrdersByConfectionerId(Long confectionerId) {
        return orderRepository.findAllByAssignedConfectionerId(confectionerId)
                .map(orderMapper::toDto);
    }

    public Mono<Void> deleteOrder(Long id) {
        return orderRepository.deleteById(id);
    }
}
