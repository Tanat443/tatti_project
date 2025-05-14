package org.example.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("shop_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopProduct {

    @Id
    private UUID id;

    @Column("shop_id")
    private UUID shopId; // ID владельца магазина

    @Column("product_id")
    private UUID productId; // ID из Product

    private boolean availableNow;  // Есть в наличии
    private Double customPrice;    // Могут менять цену
    private String deliveryTerms;  // Условия доставки

    private LocalDateTime addedAt;
}

