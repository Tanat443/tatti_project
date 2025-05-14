package com.exapmle.userservice.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopProductDTO {

    private UUID id;

    private UUID shopId; // ID владельца магазина

    private UUID productId; // ID из Product

    private boolean availableNow;  // Есть в наличии
    private Double customPrice;    // Могут менять цену
    private String deliveryTerms;  // Условия доставки
    private LocalDateTime addedAt;

}
