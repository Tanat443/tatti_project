package org.example.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.orderservice.model.OrderStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderWithNotificationDTO {
    private String notificationMessage;
    private OrderStatus status;

    public OrderWithNotificationDTO(OrderResponseDTO order, String notificationMessage) {
        this.notificationMessage = notificationMessage;
        this.status = order != null ? OrderStatus.valueOf(order.getStatus()) : null;
    }
}
