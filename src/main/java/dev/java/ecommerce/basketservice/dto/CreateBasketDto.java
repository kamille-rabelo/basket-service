package dev.java.ecommerce.basketservice.dto;

import java.util.List;

public record CreateBasketDto(
        Long clientId,
        List<ProductItemDto> products
) {
}
