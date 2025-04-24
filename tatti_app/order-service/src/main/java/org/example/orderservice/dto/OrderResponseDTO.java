package org.example.orderservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderResponseDTO {
    public Long id;
    public String orderType;
    public String description;
    public BigDecimal price;
    public String status;
    public Long customerId;
    public Long assignedConfectionerId;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    public String location;
    public LocalDateTime pushNotificationDeadline;
}
