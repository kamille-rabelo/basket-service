package dev.java.ecommerce.basketservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.java.ecommerce.basketservice.enums.PaymentMethod;
import dev.java.ecommerce.basketservice.enums.Status;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record BasketDto(
        String id,
        Long clientId,
        BigDecimal totalPrice,
        List<ProductDto>products,
        Status status,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        PaymentMethod paymentMethod
) {
}
