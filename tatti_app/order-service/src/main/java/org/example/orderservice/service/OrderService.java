package org.example.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.OrderRequestDTO;
import org.example.orderservice.dto.OrderResponseDTO;
import org.example.orderservice.dto.OrderWithNotificationDTO;
import org.example.orderservice.mapper.OrderMapper;
import org.example.orderservice.model.Order;
import org.example.orderservice.model.OrderStatus;
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
    private final NotificationService notificationService;

    public Mono<OrderWithNotificationDTO> createOrderWithNotification(OrderRequestDTO request) {
        Order order = orderMapper.toEntity(request);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        return orderRepository.save(order)
                .flatMap(savedOrder ->
                        notificationService.sendPushNotification(savedOrder.getId(), "успешно создан")
                                .map(msg -> new OrderWithNotificationDTO(orderMapper.toDto(savedOrder), msg))
                );
    }

    public Mono<OrderWithNotificationDTO> updateOrderWithNotification(Long id, OrderRequestDTO updatedDto) {
        return orderRepository.findById(id)
                .flatMap(existing -> {
                    Order updated = orderMapper.toEntity(updatedDto);
                    existing.setOrderType(updated.getOrderType());
                    existing.setDescription(updated.getDescription());
                    existing.setPrice(updated.getPrice());
                    existing.setAssignedConfectionerId(updated.getAssignedConfectionerId());
                    existing.setLocation(updated.getLocation());
                    existing.setPushNotificationDeadline(updated.getPushNotificationDeadline());
                    existing.setUpdatedAt(LocalDateTime.now());
                    return orderRepository.save(existing)
                            .flatMap(saved -> notificationService.sendPushNotification(saved.getId(), "успешно обновлён")
                                    .map(msg -> new OrderWithNotificationDTO(orderMapper.toDto(saved), msg)));
                });
    }

    public Mono<OrderWithNotificationDTO> deleteOrderWithNotification(Long id) {
        return orderRepository.findById(id)
                .flatMap(order -> orderRepository.deleteById(id)
                        .then(notificationService.sendPushNotification(id, "успешно удалён"))
                        .map(msg -> new OrderWithNotificationDTO(orderMapper.toDto(order), msg))
                );
    }

    public Mono<OrderWithNotificationDTO> changeStatusWithNotification(Long orderId, OrderStatus newStatus) {
        return orderRepository.findById(orderId)
                .flatMap(order -> {
                    if (canChangeStatus(order.getStatus(), newStatus)) {
                        order.setStatus(newStatus);
                        order.setUpdatedAt(LocalDateTime.now());
                        return orderRepository.save(order)
                                .flatMap(saved -> notificationService.sendPushNotification(saved.getId(),
                                                "статус изменён на " + newStatus.name())
                                        .map(msg -> new OrderWithNotificationDTO(orderMapper.toDto(saved), msg)));
                    } else {
                        return Mono.error(new IllegalStateException("Неверный переход статуса"));
                    }
                });
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

    private boolean canChangeStatus(OrderStatus currentStatus, OrderStatus newStatus) {
        switch (currentStatus) {
            case CREATED:
                return newStatus == OrderStatus.PUSH_PENDING || newStatus == OrderStatus.CANCELED;
            case PUSH_PENDING:
                return newStatus == OrderStatus.ACTIVE || newStatus == OrderStatus.CANCELED;
            case ACTIVE:
                return newStatus == OrderStatus.ASSIGNED || newStatus == OrderStatus.CANCELED;
            case ASSIGNED:
                return newStatus == OrderStatus.IN_PROGRESS || newStatus == OrderStatus.CANCELED;
            case IN_PROGRESS:
                return newStatus == OrderStatus.COMPLETED || newStatus == OrderStatus.CANCELED;
            default:
                return false;
        }
    }
}
