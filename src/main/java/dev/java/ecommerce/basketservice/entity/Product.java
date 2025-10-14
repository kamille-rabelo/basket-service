package dev.java.ecommerce.basketservice.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
public class Product {

    private Long id;
    private String title;
    private BigDecimal price;
    private Integer quantity;
}
