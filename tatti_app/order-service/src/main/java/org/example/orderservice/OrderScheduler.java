package org.example.orderservice;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.service.OrderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderScheduler {

    private final OrderService orderService;

    @Scheduled(fixedRate = 300000) // каждые 5 минут
    public void autoActivateOrders() {
        orderService.processPushTimeoutOrders().subscribe();
    }
}
