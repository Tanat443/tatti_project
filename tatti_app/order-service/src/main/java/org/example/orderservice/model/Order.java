package org.example.orderservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Table("orders")
public class Order {

    @Id
    private Long id;

    private String orderType;

    private String description;

    private BigDecimal price;

    private OrderStatus status;

    @Column("customer_id")
    private Long customerId;

    @Column("assigned_confectioner_id")
    private Long assignedConfectionerId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String location;

    private LocalDateTime pushNotificationDeadline;

}
