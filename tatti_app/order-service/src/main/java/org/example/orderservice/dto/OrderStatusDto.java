package org.example.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.orderservice.model.OrderStatus;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusDto {
    private OrderStatus status;
}
