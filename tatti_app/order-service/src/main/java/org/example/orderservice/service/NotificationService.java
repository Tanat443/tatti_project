package org.example.orderservice.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class NotificationService {
    public Mono<String> sendPushNotification(Long orderId, String action) {
        String message = "✅ Уведомление: операция с заказом #" + orderId + " — заказ " + action + "!\n" +
                "🧾 Номер заказа: " + orderId;
        System.out.println("[NOTIFICATION] " + message);
        return Mono.just(message);
    }
}
