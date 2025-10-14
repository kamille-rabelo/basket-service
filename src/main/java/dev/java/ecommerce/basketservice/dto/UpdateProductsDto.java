package dev.java.ecommerce.basketservice.dto;

import java.util.List;

public record UpdateProductsDto(
        List<ProductItemDto> products
) {
}
