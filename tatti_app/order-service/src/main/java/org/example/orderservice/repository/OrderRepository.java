package org.example.orderservice.repository;

import org.example.orderservice.model.Order;
import org.example.orderservice.model.OrderStatus;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface OrderRepository extends ReactiveCrudRepository<Order, Long> {
    Flux<Order> findAllByCustomerId(Long customerId);
    Flux<Order> findAllByAssignedConfectionerId(Long confectionerId);

    Flux<Order> findAllByStatusAndPushNotificationDeadlineBefore(OrderStatus status, LocalDateTime deadline);

}
