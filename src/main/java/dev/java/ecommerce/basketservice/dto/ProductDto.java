package dev.java.ecommerce.basketservice.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductDto (
        Long id,
        String title,
        BigDecimal price,
        Integer quantity
) {
}
