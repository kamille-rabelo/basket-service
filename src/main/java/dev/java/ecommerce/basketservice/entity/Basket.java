package dev.java.ecommerce.basketservice.entity;

import dev.java.ecommerce.basketservice.enums.PaymentMethod;
import dev.java.ecommerce.basketservice.enums.Status;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "basket")
public class Basket {

    @Id
    private String id;
    private Long clientId;
    @Builder.Default
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private List<Product> products;
    private Status status;
    private PaymentMethod paymentMethod;

    public void calculateTotalPrice() {
        this.totalPrice = this.products.stream()
                .map(p -> p.getPrice().multiply(BigDecimal.valueOf(p.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
