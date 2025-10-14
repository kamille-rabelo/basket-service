package dev.java.ecommerce.basketservice.dto;

import dev.java.ecommerce.basketservice.enums.PaymentMethod;

public record CheckoutBasketDto(
        PaymentMethod paymentMethod
) {
}
