package dev.java.ecommerce.basketservice.client.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public record PlatziProductDto(
        Long id,
        String title,
        BigDecimal price
) implements Serializable {
}
